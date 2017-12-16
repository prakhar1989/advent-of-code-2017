import java.io.File
import kotlin.test.assertEquals

fun main(args: Array<String>) {
    val input = File("./input/day13.txt").readLines()
    val wall = hashMapOf<Int, Int>()

    fun isScanPosAtHead(depth: Int, range: Int): Boolean {
        return (depth % (2 * range - 2)) == 0
    }

    input.forEach {
        val parts = it.split(":")
        val depth = parts[0].trim().toInt()
        val range = parts[1].trim().toInt()
        wall[depth] = range
    }

    fun hasHitWall(delay: Int): Boolean {
        return wall.keys.any { isScanPosAtHead(it+delay, wall[it]!!) }
    }

    val severity = wall.entries
            .filter { isScanPosAtHead(it.key, it.value) }
            .map { it.key * it.value }
            .sum()

    val delay = (0..Int.MAX_VALUE).takeWhile { hasHitWall(it) }.last().inc()

    assertEquals(severity, 1588)
    assertEquals(delay, 3865118)
}

