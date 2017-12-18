import java.io.File
import kotlin.test.assertEquals

enum class Operation { SET, ADD, MUL, MOD, SOUND, RECOVER, JUMP }

data class Command(val register: String, val op: Operation, val operand: String?)

private fun toCommand(s: String): Command {
    val parts = s.split(" ")
    val op = when (parts[0]) {
        "set" -> Operation.SET
        "add" -> Operation.ADD
        "mul" -> Operation.MUL
        "mod" -> Operation.MOD
        "snd" -> Operation.SOUND
        "rcv" -> Operation.RECOVER
        "jgz" -> Operation.JUMP
        else -> throw IllegalArgumentException()
    }
    val register = parts[1]
    return when (op) {
        Operation.SOUND, Operation.RECOVER -> Command(register, op, null)
        else -> Command(register, op, parts[2])
    }
}

private fun read(o: String, lookupTable: HashMap<String, Long>): Long {
    return try {
        o.toLong()
    } catch (e: NumberFormatException) {
        lookupTable[o] ?: 0
    }
}

fun main(args: Array<String>) {
    val commands = File("./input/day18.txt").readLines().map { toCommand(it) }
    val registers = hashMapOf<String, Long>()
    var lastFrequencyHeard = 0L

    fun evalAtIndex(i: Int, lookupTable: HashMap<String, Long>): Int? {
        val c = commands[i]
        val r = c.register
        when (c.op) {
            Operation.SET -> registers[r] = read(c.operand!!, lookupTable)
            Operation.ADD -> registers[r] = (registers[r] ?:0) + read(c.operand!!, lookupTable)
            Operation.MUL -> registers[r] = (registers[r] ?:0) * read(c.operand!!, lookupTable)
            Operation.MOD -> registers[r] = (registers[r] ?:0) % read(c.operand!!, lookupTable)
            Operation.SOUND -> lastFrequencyHeard = read(r, lookupTable)
            Operation.JUMP -> {
                val jump = read(c.operand!!, lookupTable).toInt()
                if (jump != 0 && read(c.register, lookupTable) > 0) {
                    return i + jump
                }
            }
            Operation.RECOVER -> {
                if (read(c.register, lookupTable) != 0L) {
                    return -1
                }
            }
        }
        return null
    }

    var pc = 0
    while (pc < commands.size && pc >= 0) {
        val jump = evalAtIndex(pc, registers)
        pc = jump ?: pc + 1
    }

    assertEquals(lastFrequencyHeard, 2951) // part 1
}
