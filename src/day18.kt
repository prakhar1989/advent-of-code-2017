import java.io.File
import java.util.*
import kotlin.collections.HashMap

enum class Operation { SET, ADD, MUL, MOD, SEND, RECEIVE, JUMP }

data class Command(val register: String, val op: Operation, val operand: String?)

private fun toCommand(s: String): Command {
    val parts = s.split(" ")
    val op = when (parts[0]) {
        "set" -> Operation.SET
        "add" -> Operation.ADD
        "mul" -> Operation.MUL
        "mod" -> Operation.MOD
        "snd" -> Operation.SEND
        "rcv" -> Operation.RECEIVE
        "jgz" -> Operation.JUMP
        else -> throw IllegalArgumentException()
    }
    val register = parts[1]
    return when (op) {
        Operation.SEND, Operation.RECEIVE -> Command(register, op, null)
        else -> Command(register, op, parts[2])
    }
}

private fun VM(
        commands: List<Command>,
        memory: HashMap<String, Long>,
        inboundQueue: ArrayDeque<Long>?,
        outboundQueue: ArrayDeque<Long>?
): Long {
    fun read(o: String): Long {
        return try {
            o.toLong()
        } catch (e: NumberFormatException) {
            memory[o] ?: 0
        }
    }

    var lastFrequencyHeard = 0L
    var pc = 0
    loop@ while (pc < commands.size && pc >= 0) {
        val c = commands[pc]
        val r = c.register
        when (c.op) {
            Operation.SET -> memory[r] = read(c.operand!!)
            Operation.ADD -> memory[r] = (memory[r] ?:0) + read(c.operand!!)
            Operation.MUL -> memory[r] = (memory[r] ?:0) * read(c.operand!!)
            Operation.MOD -> memory[r] = (memory[r] ?:0) % read(c.operand!!)
            Operation.SEND -> {
                lastFrequencyHeard = read(r)
                outboundQueue?.push(lastFrequencyHeard)
            }
            Operation.JUMP -> {
                val jump = read(c.operand!!).toInt()
                if (jump != 0 && read(c.register) > 0) {
                    pc += jump
                    continue@loop
                }
            }
            Operation.RECEIVE -> {
                if (inboundQueue == null && read(r) != 0L) {
                    break@loop
                }
                if (inboundQueue != null) {
                    if (inboundQueue.isEmpty()) {
                        Thread.sleep(5000)
                        if (outboundQueue!!.isEmpty()) {
                            break@loop
                        }
                    } else {
                        memory[r] = inboundQueue.pop()
                    }
                }
            }
        }
        pc += 1
    }
    return lastFrequencyHeard
}

fun main(args: Array<String>) {
    val commands = File("./input/day18.txt").readLines().map { toCommand(it) }

    //val part1 = VM(commands, hashMapOf(), null, null)
    //assertEquals(part1, 2951)

    val queue1 = ArrayDeque<Long>()
    val queue2 = ArrayDeque<Long>()

    val memory1 = hashMapOf("p" to 0L)
    val memory2 = hashMapOf("p" to 1L)

    Thread {
        VM(commands, memory1, queue1, queue2)
    }.run()

    Thread {
        VM(commands, memory2, queue2, queue1)
    }.run()

    println(memory1)
    println(memory2)
}

