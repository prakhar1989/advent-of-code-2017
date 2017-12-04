import java.io.File
import kotlin.test.assertEquals

fun main(args: Array<String>) {
    val lines = File("./input/day4.txt").readLines()

    val part1 = lines.filter{ hasDuplicates(it) }.count()
    assertEquals(part1, 451)

    val part2 = lines.filter { hasAnagrams(it) && hasDuplicates(it) }.count()
    assertEquals(part2, 223)
}

fun hasDuplicates(phrase: String): Boolean {
    val words = phrase.split(" ")
    return (words.size == words.toSet().size)
}

fun hasAnagrams(phrase: String): Boolean {
    fun counter(word: String): Map<Char, Int> = word.groupingBy { it }.eachCount()
    return !allPairs(phrase.split(" "))
            .any { it.first != it.second &&
                    counter(it.first) == counter(it.second) }
}
