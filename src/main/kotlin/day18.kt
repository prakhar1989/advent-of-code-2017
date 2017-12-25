import kotlinx.coroutines.experimental.TimeoutCancellationException
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.experimental.runBlocking
import kotlinx.coroutines.experimental.withTimeout
import java.io.File
import kotlin.test.assertEquals

enum class Operation { SET, ADD, MUL, MOD, SEND, RECEIVE, JUMP }

private data class Command(val register: String, val op: Operation, val operand: String?)

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

// part 2
private suspend fun VM(
        id: Long,
        commands: List<Command>,
        inbound: Channel<Long>,
        outbound: Channel<Long>
): Long {
    val memory = hashMapOf("p" to id)

    fun read(o: String?): Long {
        return try {
            o?.toLong() ?: 0
        } catch (e: NumberFormatException) {
            memory.getOrDefault(o, 0)
        }
    }

    var programCounter = 0
    var sentMessages = 0L

    loop@ while (programCounter < commands.size && programCounter >= 0) {
        val c = commands[programCounter]
        val r = c.register
        when (c.op) {
            Operation.SET -> memory[r] = read(c.operand)
            Operation.ADD -> memory[r] = memory.getOrDefault(r, 0) + read(c.operand)
            Operation.MUL -> memory[r] = memory.getOrDefault(r, 0) * read(c.operand)
            Operation.MOD -> memory[r] = memory.getOrDefault(r, 0) % read(c.operand)
            Operation.SEND -> outbound.send(read(r)).also { sentMessages++ }
            Operation.JUMP -> {
                val jump = read(c.operand).toInt()
                if (jump != 0 && read(c.register) > 0) {
                    programCounter += jump
                    continue@loop
                }
            }
            Operation.RECEIVE -> {
                try {
                    memory[r] = withTimeout(100) { inbound.receive() }
                } catch (e: TimeoutCancellationException) {
                    break@loop // deadlock reached
                }
            }
        }
        programCounter++
    }
    return sentMessages
}

fun main(args: Array<String>) {
    val commands = File("input/day18.txt").readLines().map { toCommand(it) }

    runBlocking {
        val queue1 = Channel<Long>(UNLIMITED) // infinite buffered channels
        val queue2 = Channel<Long>(UNLIMITED)
        async { VM(0L, commands, queue1, queue2) }
        val vm1 = async { VM(1L, commands, queue2, queue1) }

        assertEquals(7366, vm1.await())
    }
}
