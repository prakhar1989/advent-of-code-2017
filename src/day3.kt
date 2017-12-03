import kotlin.math.sqrt
import kotlin.test.assertEquals

fun main(args: Array<String>) {
    // part 1
    assertEquals(getManhattanDistance(25), 4)
    assertEquals(getManhattanDistance(1024), 31)
    assertEquals(getManhattanDistance(9), 2)
    assertEquals(getManhattanDistance(265149), 438)

    // part 2
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
