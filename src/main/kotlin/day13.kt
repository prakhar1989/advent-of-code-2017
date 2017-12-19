import java.io.File
import kotlin.test.assertEquals

fun main(args: Array<String>) {
    val input = File("input/day13.txt").readLines()

    val wall = hashMapOf<Int, Int>()
    input.forEach {
        val parts = it.split(":").map { it.trim().toInt() }
        val (depth, range) = parts
        wall[depth] = range
    }

    fun isScanPosAtHead(depth: Int, range: Int): Boolean {
        return (depth % (2 * range - 2)) == 0
    }

    fun hasHitWall(delay: Int): Boolean {
        return wall.entries.any {
            isScanPosAtHead(delay + it.key, it.value)
        }
    }

    val severity = wall.entries
            .filter { isScanPosAtHead(it.key, it.value) }
            .map { it.key * it.value }
            .sum()

    val delay = (0..Int.MAX_VALUE).takeWhile { hasHitWall(it) }.last().inc()

    assertEquals(1588, severity)
    assertEquals(3865118, delay)
}
