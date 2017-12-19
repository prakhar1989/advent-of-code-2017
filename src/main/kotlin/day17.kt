import kotlin.test.assertEquals

fun main(args: Array<String>) {
    val input = 324

    var currPos = 0
    val buffer = mutableListOf(0)
    for (i in 1..2017) {
        currPos = (currPos + input) % i
        buffer.add(currPos++, i)
    }
    // part 1
    assertEquals(1306, buffer[buffer.indexOf(2017) + 1])

    // part 2
    var lastSeen: Int = -100
    currPos = 0
    for (i in 1..50_000_000) {
        currPos = (currPos + input) % i
        if (currPos++ == 0) {
           lastSeen = i
        }
    }
    assertEquals(20430489, lastSeen)
}