import java.lang.Integer.parseInt
import kotlin.test.assertEquals

fun main(args: Array<String>) {
    val input = "xlqgujun"
    val grid: List<String> = (0..127).map {toBinary(input + "-$it")}
    assertEquals(part1(grid), 8204)

    val seen = hashSetOf<Pair<Int, Int>>()
    var regions = 0

    fun dfs(i: Int, j: Int) {
        if (Pair(i, j) in seen || grid[i][j] == '0') {
            return
        }
        seen.add(Pair(i, j))
        if (i > 0) dfs(i-1, j)
        if (j > 0) dfs(i, j-1)
        if (i < 127) dfs(i+1, j)
        if (j < 127) dfs(i, j+1)
    }

    for (i in 0..127) {
        for (j in 0..127) {
            if (Pair(i, j) in seen || grid[i][j] == '0') {
                continue
            }
            regions++
            dfs(i, j)
        }
    }

    assertEquals(regions, 1089)
}


private fun part1(grid: List<String>): Int {
    return grid.map { it.count { it == '1'} }.sum()
}

private fun toBinary(s: String): String {
    return knotHash(s)
            .map { parseInt(it.toString(), 16)
                    .toString(2)
                    .padStart(4, '0')             }
            .joinToString("")
}