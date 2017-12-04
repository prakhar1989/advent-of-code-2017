import kotlin.math.sqrt
import kotlin.test.assertEquals

fun main(args: Array<String>) {
    // part 1
    assertEquals(getManhattanDistance(25), 4)
    assertEquals(getManhattanDistance(1024), 31)
    assertEquals(getManhattanDistance(9), 2)
    assertEquals(getManhattanDistance(265149), 438)

    // part 2
    assertEquals(part2(800), 806)
    assertEquals(part2(265149), 266330)
}

private fun part2(input: Int): Int {
    val grid = HashMap<Pair<Int, Int>, Int>()
    grid[Pair(0, 0)] = 1

    for (s in 1 until 300) {
        // up
        for (y in -(s-1)..s) {
            grid[Pair(s, y)] = getNeighborsSum(grid, s, y)
            if (grid[Pair(s, y)]!! > input) {
                return grid[Pair(s, y)]!!
            }
        }

        // left
        for (x in (s-1 downTo -s)) {
            grid[Pair(x, s)] = getNeighborsSum(grid, x, s)
            if (grid[Pair(x, s)]!! > input) {
                return grid[Pair(x, s)]!!
            }
        }

        // down
        for (y in (s-1 downTo -s)) {
            grid[Pair(-s, y)] = getNeighborsSum(grid, -s, y)
            if (grid[Pair(-s, y)]!! > input) {
                return grid[Pair(-s, y)]!!
            }
        }

        // right
        for (x in -(s-1)..s) {
            grid[Pair(x, -s)] = getNeighborsSum(grid, x, -s)
            if (grid[Pair(x, -s)]!! > input) {
                return grid[Pair(x, -s)]!!
            }
        }
    }
    return 0
}

private fun getManhattanDistance(x: Int): Int {
    val n = nextOddSqrt(x)
    val sideOfSquare = n-1 // side of square
    val m = Math.round((n*n - x)/sideOfSquare.toDouble()).toInt()
    val nearestCorner = n*n - m*sideOfSquare
    return sideOfSquare/2 + (sideOfSquare/2 - Math.abs(nearestCorner - x))
}

private fun nextOddSqrt(x: Int): Int {
    val sqrt = Math.ceil(sqrt(x.toDouble())).toInt()
    return if (sqrt % 2 == 1) sqrt else sqrt + 1
}

private fun getNeighborsSum(grid: Map<Pair<Int, Int>, Int>, x: Int, y: Int): Int {
    return listOf(Pair(x-1, y-1), Pair(x-1,y), Pair(x-1, y+1), Pair(x, y+1),
                Pair(x+1, y-1), Pair(x+1, y), Pair(x+1, y+1), Pair(x, y-1))
            .map{grid[Pair(it.first, it.second)] ?: 0}
            .sum()
}
