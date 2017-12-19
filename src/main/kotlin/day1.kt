import java.io.File
import kotlin.test.assertEquals

fun Char.toDigit() = this.minus('0')

fun main(args: Array<String>) {
    val input = File("input/day1.txt").readText()

    assertEquals(getSum(input), 1390) // part 1
    assertEquals(getSum(input, input.length/2), 1232) // part 2

    // functional approach
    assertEquals(getSumFunctional(input), 1390) // part 1
    assertEquals(getSumFunctional(input, input.length/2), 1232) // part 2

    // alternative functional
    assertEquals(getSumFunctionalV2(input, input.length), 1390) // part 1
    assertEquals(getSumFunctionalV2(input, input.length, input.length/2), 1232) // part 2
}

private fun getSum(s: String, jump: Int = 1): Int {
    var total = 0
    val n = s.length
    for (i in s.indices) {
        if (s[i] == s[(i + jump) % n]) {
            total += s[i].toDigit()
        }
    }
    return total
}

private fun getSumFunctional(s: String, jump: Int = 1): Int {
    val n = s.length
    return (0 until n)
            .filter { s[it] == s[(it + jump) % n] }
            .map { s[it].toDigit() }
            .sum()
}

private fun getSumFunctionalV2(s: String, n: Int, jump: Int = 1): Int {
    return s.filterIndexed {i, c -> c == s[(i + jump) % n]}
            .map { it.toDigit() }
            .sum()
}
