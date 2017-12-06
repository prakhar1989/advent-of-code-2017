import java.io.File
import kotlin.test.assertEquals

fun main(args: Array<String>) {
    val input = File("./input/day5.txt").readLines().map { it.toInt() }
    assertEquals(396086, solution(input.toMutableList()))
    assertEquals(28675390, solution(input.toMutableList(), false))
}

private fun solution(input: MutableList<Int>, part1: Boolean = true): Int {
    var currPos = 0
    var step = 0
    while (currPos < input.size) {
        val newCurrPos = currPos + input[currPos]
        val incr = if (part1) 1 else if (input[currPos] >= 3) -1 else 1
        input[currPos] += incr
        currPos = newCurrPos
        step += 1
    }
    return step
}
