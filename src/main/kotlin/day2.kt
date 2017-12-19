import java.io.File
import kotlin.test.assertEquals

fun main(args: Array<String>) {
    val numbers = File("input/day2.txt")
            .readLines()
            .map { it.split("\t") .map { it.toInt() } }

    val part1 = numbers.map { it.max()!! - it.min()!! }.sum()
    val part2 = numbers.map { findDivisor(it) }.sum()

    assertEquals(part1, 37923)
    assertEquals(part2, 263)
}

private fun findDivisor(xs: List<Int>): Int {
    return allPairs(xs)
            .filter { it.first != it.second }
            .first { it.first % it.second == 0 }
            .run { this.first / this.second }
}

// imperative approach
private fun findDivisorImp(xs: List<Int>): Int {
    for (i in 0 until xs.size) {
        for (j in i+1 until xs.size) {
            val a = xs[i]
            val b = xs[j]
            if (a % b == 0 || b % a == 0) {
                return if (a > b) a / b else b / a
            }
        }
    }
    return 0
}
