import java.io.File
import kotlin.math.abs
import kotlin.test.assertEquals

fun main(args: Array<String>) {
    val input = File("input/day11.txt").readText()
    assertEquals(part1("ne,ne,sw,sw"), 0)
    assertEquals(part1("ne,ne,ne"), 3)
    assertEquals(part1("ne,ne,s,s"), 2)
    assertEquals(part1("se,sw,se,sw,sw"), 3)
    assertEquals(part1(input), 722)
}

// Thanks to: https://www.redblobgames.com/grids/hexagons/#distances-cube
private fun part1(s: String): Int {
    val input = s.trim().split(",")
    val dirs = hashMapOf(
            "ne" to Pair(1, -1),
            "n" to Pair(0, -1),
            "nw" to Pair(-1, 0),
            "s" to Pair(0, 1),
            "se" to Pair(1, 0),
            "sw" to Pair(-1, 1)
    )

    var curr = Pair(0, 0)
    var maxAway = 0

    for (i in input) {
        val d = dirs[i]!!
        curr = Pair(curr.first + d.first, curr.second + d.second)
        if (dist(curr) > maxAway) {
            maxAway = dist(curr)
        }
    }
    //println(maxAway)
    return dist(curr)
}

private fun dist(p: Pair<Int, Int>): Int {
    return (abs(p.first) + abs(p.second) + (abs(p.first + p.second)))/2
}
