import java.io.File

fun main(args: Array<String>) {
    val input = File("input/day12.txt").readLines()
    val graph = hashMapOf<String, List<String>>()

    input.forEach {
        val parts = it.split("<->").map { it.trim() }
        graph[parts[0]] = parts[1].split(",").map { it.trim() }
    }

    // part1
    val reachable = hashSetOf("0")
    dfs("0", graph, reachable)
    println(reachable.size)

    // part2
    val reachableNodes = hashMapOf<String, Set<String>>()
    val seenNodes = hashSetOf<String>()
    for (node in graph.keys) {
        if (node !in seenNodes) {
            val connectedNodes = hashSetOf(node)
            dfs(node, graph, connectedNodes)
            seenNodes.addAll(connectedNodes)
            reachableNodes[node] = connectedNodes
        }
    }
    println(reachableNodes.size)

}

private fun dfs(node: String, graph: HashMap<String, List<String>>, seen: HashSet<String>) {
    for (child in graph[node]!!) {
        if (child !in seen) {
            seen.add(child)
            dfs(child, graph, seen)
        }
    }
}
