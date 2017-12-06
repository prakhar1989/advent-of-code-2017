import java.io.File
import kotlin.test.assertEquals

fun main(args: Array<String>) {
    val input = File("./input/day6.txt").readText()
            .split("\t")
            .map{ it.toInt() }
            .toMutableList()

    assertEquals(part1(input), 4074)
    assertEquals(part2(input), 2793)
}

private fun part2(input: MutableList<Int>): Int {
    val seen = hashSetOf<List<Int>>()
    val seenWhen = hashMapOf<List<Int>, Int>()
    var step = 0
    while (input !in seen) {
        seen.add(input.toList())
        seenWhen[input.toList()] = step
        balanceInput(input)
        step += 1
    }
    return step - seenWhen[input]!!
}

private fun part1(input: MutableList<Int>): Int {
    val seen = hashSetOf<List<Int>>()
    var step = 0
    while (input !in seen) {
        seen.add(input.toList())
        balanceInput(input)
        step += 1
    }
    return step
}

private fun balanceInput(input: MutableList<Int>) {
    var (m, maxi) = input.withIndex().maxBy { it.value }!!
    input[m] = 0
    for (v in maxi downTo 1) {
        input[(++m) % input.size] += 1
    }
}
