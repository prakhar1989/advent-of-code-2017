import java.io.File

fun Char.toIndex() = this.toDigit() - 'a'.toDigit()

fun main(args: Array<String>) {

    val commands = File("input/day23.txt").readLines()
    val memory = IntArray(16)

    fun read(r: String): Int {
        return try {
            r.toInt()
        } catch (e: NumberFormatException) {
            memory[r[0].toIndex()]
        }
    }

    var programCounter = 0
    var mulTimes = 0

    loop@ while (programCounter >= 0 && programCounter < commands.size) {
        val (op, x, y) = commands[programCounter].split(" ")
        val command = commands[programCounter]
        val registerIndex = x[0].toIndex()
        val operand = read(y)

        when (op) {
            "set" -> memory[registerIndex] = operand
            "sub" -> memory[registerIndex] -= operand
            "mul" -> {
                mulTimes++
                memory[registerIndex] *= operand
            }
            "jnz" -> {
                if (read(x) != 0) {
                    programCounter += operand
                    continue@loop
                }
            }
            else -> throw IllegalArgumentException()
        }
        programCounter++
    }
    println(mulTimes)

    // part 2 done thanks to:
    // https://www.reddit.com/r/adventofcode/comments/7lms6p/2017_day_23_solutions/drnjwq7/
}
