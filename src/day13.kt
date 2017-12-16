import java.io.File
import kotlin.math.abs
import kotlin.math.min
import kotlin.test.assertEquals

fun main(args: Array<String>) {
    val input = File("./input/day13.txt").readLines().map {
                val parts = it.split(": ").map{it.toInt()}
                Pair(parts[0], parts[1])
            }
    val lastScanPos = input.map { it.first }.max()!!
    val firewall = IntArray(lastScanPos + 1)
    input.forEach { firewall[it.first] = it.second }
    assertEquals(1588, part1(firewall, lastScanPos))
}

private fun part1(firewall: IntArray, lastScanPos: Int): Int {
    var runningScore = 0

    for (t in 0..lastScanPos) {
        for (j in firewall.indices) {
            if (firewall[j] > 0) {
                val index = indexAtT(t, firewall[j])
                if (index == 0 && j == t) {
                    runningScore += (firewall[j] * t)
                }
            }
        }
    }
    return runningScore
}

private fun indexAtT(t: Int, range: Int): Int {
    val k = 2* range - 2
    return min(t % k, abs(t - k))
}

