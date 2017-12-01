import java.io.File
import kotlin.test.assertEquals

fun main(args: Array<String>) {
    val input = File("./input/day1.txt").readText()
    assertEquals(part1(input), 1390)
    val jump = input.length / 2
    assertEquals(part1(input, jump), 1232)
}

fun part1(s: String, jump: Int = 1): Int {
    var total = 0
    val n = s.length
    for (i in s.indices) {
        if (s[i] == s[(i + jump) % n]) {
            total += s[i].toString().toInt()
        }
    }
    return total
}
