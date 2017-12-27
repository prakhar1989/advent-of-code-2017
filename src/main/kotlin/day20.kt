import java.io.File
import kotlin.math.abs

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

    fun collide(): Set<Particle> {
        val collided = hashSetOf<Particle>()
        for (p in particles) {
            particles
                .filter { p != it && p.pos == it.pos }
                .forEach { collided.addAll(listOf(p, it)) }
        }
        return collided
    }

    // 1000 is good enough for 'long term'
    for (i in 1..1000) {
        particles.forEach { it.tick() }
        collide().forEach { particles.remove(it) }
    }

    //val closest = particles.indices.minBy { particles[it].dist() }
    //println(closest)  // part 1
    println(particles.size) // part 2
}