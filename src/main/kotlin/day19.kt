import java.io.File
import kotlin.test.assertEquals

private enum class Dir {UP, DOWN, LEFT, RIGHT}

private data class Point(val x: Int, val y: Int) {
    fun add(p: Point) = Point(x + p.x, y + p.y)
    fun liesWithin(input: List<String>): Boolean =
            x >= 0 && y >= 0 && x < input.size && y < input[x].length
    fun valueIn(input: List<String>) = input[x][y]
}

private fun moveInDir(dir: Dir, p: Point): Point {
    return p.add(when (dir) {
        Dir.DOWN -> Point(1, 0)
        Dir.UP -> Point(-1, 0)
        Dir.RIGHT -> Point(0, 1)
        Dir.LEFT -> Point(0, -1)
    })
}

fun main(args: Array<String>) {
    var stepsTaken = 0
    var seen = mutableListOf<Char>()

    val input = File("input/day19.txt").readLines()
    var currPoint = Point(0, input[0].indexOf('|'))
    var currDir = Dir.DOWN

    fun turn() {
        val possibleDirections = when (currDir) {
            Dir.UP, Dir.DOWN -> listOf(Dir.RIGHT, Dir.LEFT)
            else -> listOf(Dir.UP, Dir.DOWN)
        }

        currDir = possibleDirections.first {
            val p = moveInDir(it, currPoint)
            p.liesWithin(input) && p.valueIn(input) != ' '
        }
    }

    loop@ while (true) {
        val cursor = currPoint.valueIn(input)
        when (cursor) {
            in 'A'..'Z' -> seen.add(cursor)
            '+' -> turn()
            ' ' -> break@loop
            else -> { /* no op */}
        }
        stepsTaken++
        currPoint = moveInDir(currDir, currPoint)
    }

    assertEquals("EPYDUXANIT", seen.joinToString(""))
    assertEquals(17544, stepsTaken)
}