import java.io.File
import kotlin.test.assertEquals

fun main(args: Array<String>) {
    val input = File("./input/day8.txt").readLines()

    val results = hashMapOf<String, Int>()
    var runningMax = 0

    input.forEach {
        val parts = it.split(" ")
        val register = parts[0]
        val op = parts[1]
        val constant = parts[2].toInt()

        if(evalCondition(parts[4], parts[5], parts[6].toInt(), results)) {
            when (op) {
                "inc" -> results[register] = (results[register] ?: 0) + constant
                else -> results[register] = (results[register] ?: 0) - constant
            }
        }
        val currMax = results.values.max() ?: 0
        if (currMax > runningMax) {
            runningMax = currMax
        }
    }

    assertEquals(results.values.max(), 6012) // part 1
    assertEquals(runningMax, 6369) // part2
}

fun evalCondition(reg: String, op: String, constant: Int, lookup: HashMap<String, Int>): Boolean {
    val curr = lookup[reg] ?: 0
    return when (op) {
        ">" -> curr > constant
        "<" -> curr < constant
        "==" -> curr == constant
        ">=" -> curr >= constant
        "!=" -> curr != constant
        else -> curr <= constant // "<=" ->
    }
}
