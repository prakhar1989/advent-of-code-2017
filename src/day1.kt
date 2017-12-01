import java.io.File
import kotlin.test.assertEquals

fun main(args: Array<String>) {
    val input = File("./input/day1.txt").readText()

    assertEquals(getSum(input), 1390) // part 1
    assertEquals(getSum(input, input.length/2), 1232) // part 2

    // functional approach
    assertEquals(getSumFunctional(input), 1390) // part 1
    assertEquals(getSumFunctional(input, input.length/2), 1232) // part 2
}

fun getSum(s: String, jump: Int = 1): Int {
    var total = 0
    val n = s.length
    for (i in s.indices) {
        if (s[i] == s[(i + jump) % n]) {
            total += s[i].toInt() - 48
        }
    }
    return total
}

fun getSumFunctional(s: String, jump: Int = 1): Int {
    val n = s.length
    return (0 until n)
            .filter { s[it] == s[(it + jump) % n] }
            .map { s[it].toInt() - 48 }
            .sum()
}
