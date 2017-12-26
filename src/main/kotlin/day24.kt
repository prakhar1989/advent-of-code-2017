import java.io.File

private data class Edge(val a: Int, val b: Int) {
    val value = a + b
    fun canJoinWith(x: Int) = a == x || b == x
    fun otherEndOf(x: Int) = if (a == x) b else a
}

private typealias Bridge = List<Edge>

private fun totalValue(bridge: Bridge): Int {
    return bridge.map { it.value }.sum()
}

private fun buildBridge(node: Int, bridge: Bridge, remaining: Bridge, comparator: Comparator<Bridge>): Bridge {
    val s: List<Bridge> =  remaining.filter { it.canJoinWith(node) }
            .map { buildBridge(it.otherEndOf(node), bridge + it, remaining - it, comparator)}
    return s.maxWith(comparator) ?: bridge
}

fun main(args: Array<String>) {
    val bridge = File("input/day24.txt").readLines()
            .map { it.split("/").map { it.toInt() } }
            .map { (a, b) -> Edge(a, b) }

    val part1 = totalValue(buildBridge(0, listOf(), bridge, compareBy { totalValue(it) }))
    println(part1)

    val part2 = totalValue(buildBridge(0, listOf(), bridge, compareBy(Bridge::size) then compareBy { totalValue(it) }))
    println(part2)
}