import java.io.File
import kotlin.test.assertEquals

val swapRegex = Regex("s([0-9]+)")
val xchangeRegex = Regex("x([0-9]+)/([0-9]+)")
val partnerRegex = Regex("p([a-zA-Z]+)/([a-zA-Z]+)")

fun main(args: Array<String>) {
    val moves = File("./input/day16.txt").readText().split(',')

    fun swapProgram(program: MutableList<Char>, i: Int, j: Int) {
        val tmp = program[i]
        program[i] = program[j]
        program[j] = tmp
    }

    fun dance(program: String): String {
        var next = program.toMutableList()
        for (move in moves) {
            when (move[0]) {
                's' -> {
                    val shift = swapRegex.matchEntire(move)?.groupValues?.get(1)?.toInt()!!
                    val n = next.size
                    next = (next.takeLast(shift) + next.take(n-shift)).toMutableList()
                }
                'x' -> {
                    val (i, j) = xchangeRegex.matchEntire(move)!!.groupValues.takeLast(2).map{it.toInt()}
                    swapProgram(next, i, j)
                }
                'p' -> {
                    val (a, b) = partnerRegex.matchEntire(move)!!.groupValues.takeLast(2)
                    val i = next.indexOf(a.single())
                    val j = next.indexOf(b.single())
                    swapProgram(next, i, j)
                }
                else -> throw Error("parse error")
            }
        }
        return next.joinToString("")
    }

    fun part2(): String {
        val seenDances = mutableListOf<String>()
        var next = "abcdefghijklmnop"
        repeat (1_000_000_000) {
            if (seenDances.contains(next)) {
                return seenDances[1_000_000_000 % seenDances.size]
            } else {
                seenDances.add(next)
                next = dance(next)
            }
        }
        return next
    }

    assertEquals(dance("abcdefghijklmnop"), "giadhmkpcnbfjelo") // part 1
    assertEquals(part2(), "njfgilbkcoemhpad") // part2
}