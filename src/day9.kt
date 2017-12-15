import java.io.File
import java.util.*

fun main(args: Array<String>) {
    val input = File("./input/day9.txt").readText()
    println(getScore(input))
}

private fun getScore(input: String): Int {
    val stack = ArrayDeque<Char>()
    var score = 0
    var garbage = 0
    var i = 0
    var runningscore = 0
    while (i < input.length){
        val c = input[i]
        if (c == '{'){
            runningscore += 1
            score += runningscore
            stack.add(c)
        }
        else if (c == '}') {
            runningscore -= 1
            if (stack.size > 0) {
                stack.pop()
            }
        }
        else if (c == '<'){
            while(!(input[i] == '>')){
                if(input[i] == '!')
                {
                    i++
                    garbage--
                }
                i++
                garbage++
            }
            garbage--
        }
        i++
    }
    println("garbage: $garbage")
    return score
}
