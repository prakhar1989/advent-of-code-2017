import java.io.File
import kotlin.test.assertEquals

fun main(args: Array<String>) {
    val input = File("./input/day10.txt").readText()
            .split(',').map { it.toInt() }

    assertEquals(knotHash(""), "a2582a3a0e66e6e86e3812dcb672a272")
    assertEquals(knotHash("1,2,3"), "3efbe78a8d82f29979031a4aa0b16a9d")
}

private fun toHex(i: Int): String {
    if (i < 16) {
        return '0' + i.toString(16)
    }
    return i.toString(16)
}

private fun part1(input: List<Int>): Int {
    val n = 256
    val list = (0..(n-1)).toMutableList()
    var currPos = 0
    var skipSize = 0

    // part 1
    for (i in input) {
        val tempList = (0..(i-1))
                .map { list[(currPos + it) % n] }
                .reversed()

        (0..(i-1)).forEach {
            val index = (currPos + it) % n
            list[index] = tempList[it]
        }

        currPos = (currPos + i + skipSize) % n
        skipSize++
    }
    return list[0] * list[1]
}

fun knotHash(s: String): String {
    val asciiInput = s.trim().map { it.toInt() }.toMutableList()
    asciiInput.addAll(listOf(17, 31, 73, 47, 23))

    val n = 256
    val list = (0..(n-1)).toMutableList()
    var currPos = 0
    var skipSize = 0

    (0..63).forEach {
        for (i in asciiInput) {
            val tempList = (0..(i-1))
                    .map { list[(currPos + it) % n] }
                    .reversed()

            (0..(i-1)).forEach {
                val index = (currPos + it) % n
                list[index] = tempList[it]
            }

            currPos = (currPos + i + skipSize) % n
            skipSize++
        }
    }

    return (0..255 step 16)
            .map {(it..(it + 15))
                    .map{j -> list[j]}
                    .reduceRight{a, b -> a.xor(b)}}
            .map{toHex(it)}
            .joinToString("")
}