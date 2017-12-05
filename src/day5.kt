import java.io.File
import kotlin.test.assertEquals

fun main(args: Array<String>) {
    val input = File("./input/day5.txt").readLines().map { it.toInt() }.toIntArray()
    assertEquals(396086, part1(input.copyOf()))
    assertEquals(28675390, part2(input.copyOf()))
}

private fun part2(input: IntArray): Int {
    var currPos = 0
    var newCurrPos: Int
    var step = 0
    while (currPos < input.size) {
        newCurrPos = currPos + input[currPos]
        input[currPos] = input[currPos] + if (input[currPos] >= 3) -1 else 1
        currPos = newCurrPos
        step += 1
    }
    return step
}

private fun part1(input: IntArray): Int {
    var currPos = 0
    var oldCurrPos = 0
    var step = 0
    while (currPos < input.size) {
        oldCurrPos = currPos
        currPos += input[currPos]
        input[oldCurrPos] += 1
        step += 1
    }
    return step
}