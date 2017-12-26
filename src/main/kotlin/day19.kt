import java.io.File
import kotlin.test.assertEquals

fun main(args: Array<String>) {
    val directions = listOf(Pair(1, 0), Pair(-1, 0), Pair(0, -1), Pair(0, 1))

    var dx = 1
    var dy = 0
    var x = 0
    var y = 0
    var stepsTaken = 0
    var seen = mutableListOf<Char>()

    val input = File("input/day19.txt").readLines()
    y = input[0].indexOf('|')

    fun turn() {
        val newDirection = directions.find {
            if (it == Pair(-dx, -dy)) {
                false
            } else {
                val newX = x + it.first
                val newY = y + it.second
                if (newX < 0 || newY < 0 || newX >= input.size || newY >= input[x].length) {
                    false
                } else {
                    input[newX][newY] != ' '
                }
            }
        } ?: throw IllegalStateException()

        dx = newDirection.first
        dy = newDirection.second
    }

    loop@ while (true) {
        val cursor = input[x][y]
        when (cursor) {
            in 'A'..'Z' -> seen.add(cursor)
            '+' -> turn()
            ' ' -> break@loop
            else -> { /* no op */}
        }
        stepsTaken++
        x += dx
        y += dy
    }

    assertEquals("EPYDUXANIT", seen.joinToString(""))
    assertEquals(17544, stepsTaken)
}

