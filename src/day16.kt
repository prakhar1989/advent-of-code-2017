import java.io.File
import kotlin.test.assertEquals

val swapRegex = Regex("s([0-9]+)")
val xchangeRegex = Regex("x([0-9]+)/([0-9]+)")
val partnerRegex = Regex("p([a-zA-Z]+)/([a-zA-Z]+)")

fun main(args: Array<String>) {
    val input = File("./input/day16.txt").readText()
    val moves = input.split(',')


    fun swapProgram(program: MutableList<Char>, i: Int, j: Int) {
        val tmp = program[i]
        program[i] = program[j]
        program[j] = tmp
    }


    fun dance(program: List<Char>): String {
        var program = program.toMutableList()
        for (move in moves) {
            val m = move[0]
            when (m) {
                's' -> {
                    val shift = swapRegex.matchEntire(move)?.groupValues?.get(1)?.toInt()!!
                    val n = program.size
                    program = (program.takeLast(shift) + program.take(n-shift)).toMutableList()
                }
                'x' -> {
                    val (i, j) = xchangeRegex.matchEntire(move)!!.groupValues.takeLast(2).map{it.toInt()}
                    swapProgram(program, i, j)
                }
                'p' -> {
                    val (a, b) = partnerRegex.matchEntire(move)!!.groupValues.takeLast(2)
                    val i = program.indexOf(a.single())
                    val j = program.indexOf(b.single())
                    swapProgram(program, i, j)
                }
                else -> throw Error("parse error")
            }
        }
        return program.joinToString("")
    }

    val seenDances = mutableListOf<String>()
    var program = "abcdefghijklmnop"
    for (i in 1..1_000_000_000) {
        if (seenDances.contains(program)) {
            break
        } else {
            seenDances.add(program)
            program = dance(program.toList())
        }
    }
    assertEquals(dance("abcdefghijklmnop".toList()), "giadhmkpcnbfjelo") // part 1
    assertEquals(seenDances[1_000_000_000 % seenDances.size], "njfgilbkcoemhpad") // part2
}