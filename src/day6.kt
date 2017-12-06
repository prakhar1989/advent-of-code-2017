import java.io.File
import kotlin.test.assertEquals

fun main(args: Array<String>) {
    val input = File("./input/day6.txt").readText()
            .split("\t")
            .map{ it.toInt() }
            .toMutableList()

    assertEquals(4074, solution(input).first)  // part 1
    assertEquals(2793, solution(input).second) // part 2
}

private fun solution(input: MutableList<Int>): Pair<Int, Int> {
    val seen = hashSetOf<List<Int>>()
    val seenAtStep = hashMapOf<List<Int>, Int>()
    while (input !in seen) {
        seen.add(input.toList())
        seenAtStep[input.toList()] = seen.size
        balanceInput(input)
    }
    return Pair(seen.size, seen.size + 1 - seenAtStep[input]!!)
}

private fun balanceInput(input: MutableList<Int>) {
    var (m, maxi) = input.withIndex().maxBy { it.value }!!
    input[m] = 0
    for (v in maxi downTo 1) {
        input[(++m) % input.size] += 1
    }
}
