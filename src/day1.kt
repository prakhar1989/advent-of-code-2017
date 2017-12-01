import java.io.File
import kotlin.test.assertEquals

fun main(args: Array<String>) {
    val input = File("./input/day1.txt").readText()
    assertEquals(getSum(input), 1390) // part 1
    val jump = input.length / 2
    assertEquals(getSum(input, jump), 1232) // part 2
}

fun getSum(s: String, jump: Int = 1): Int {
    var total = 0
    val n = s.length
    for (i in s.indices) {
        if (s[i] == s[(i + jump) % n]) {
            total += s[i].toString().toInt()
        }
    }
    return total
}
