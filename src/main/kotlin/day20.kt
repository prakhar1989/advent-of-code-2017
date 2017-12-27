import java.io.File
import kotlin.math.abs
import kotlin.test.assertEquals

private data class Triple(val a: Int, val b: Int, val c: Int) {
    fun add(t: Triple) = Triple(t.a + a, t.b + b, t.c + c)
}
private class Particle(var pos: Triple, var velocity: Triple, val accel: Triple) {
    fun tick() {
        velocity = velocity.add(accel)
        pos = pos.add(velocity)
    }

    // computes the manhattan distance between two triples
    fun dist(p: Triple = Triple(0, 0, 0)): Int {
        return abs(pos.a - p.a) + abs(pos.b - p.b) + abs(pos.c - p.c)
    }
}

private fun toParticle(s: String): Particle {
    val r = Regex("p=<\\s*(-?\\d+),\\s*(-?\\d+),\\s*(-?\\d+)>, v=<\\s*(-?\\d+),\\s*(-?\\d+),\\s*(-?\\d+)>, a=<\\s*(-?\\d+),\\s*(-?\\d+),\\s*(-?\\d+)>")
    if (r.matches(s)) {
        val parts = r.matchEntire(s)!!.groupValues!!.toList().takeLast(9).map { it.toInt() }
        return Particle(Triple(parts[0], parts[1], parts[2]),
                Triple(parts[3], parts[4], parts[5]),
                Triple(parts[6], parts[7], parts[8]))
    }
    throw IllegalArgumentException()
}

fun main(args: Array<String>) {
    val particles = File("input/day20.txt").readLines()
            .map { toParticle(it) }.toMutableList()

    //assertEquals(258, part1(particles))
    assertEquals(707, part2(particles))
}

private fun part1(particles: List<Particle>, times: Int = 1000): Int {
    repeat(times) {
        particles.forEach { it.tick() }
    }
    return particles.indices.minBy { particles[it].dist() } ?: 0
}

private fun part2(ps: List<Particle>, times: Int = 100): Int {
    val particles = ps.toMutableList()
    repeat(times) {
        particles.forEach { it.tick() }
        val collisions = particles.groupBy { it.pos }.values.filter { it.size > 1 }.flatten()
        particles.removeAll(collisions)
    }
    return particles.size
}