private enum class Direction { LEFT, RIGHT }
private typealias State = Char

private data class Rule(
    val value: Int,
    val direction: Direction,
    val nextState: State
)

fun main(args: Array<String>) {
//    val rules = hashMapOf<State, Pair<Rule, Rule>>(
//            'A' to Pair(Rule(1, Direction.RIGHT, 'B'),
//                    Rule(0, Direction.RIGHT, 'F')),
//            'B' to Pair(Rule(0, Direction.LEFT, 'B'),
//                    Rule(1, Direction.LEFT, 'C')),
//            'C' to Pair(Rule(1, Direction.LEFT, 'D'),
//                    Rule(0, Direction.RIGHT, 'C')),
//            'D' to Pair(Rule(1, Direction.LEFT, 'E'),
//                    Rule(1, Direction.RIGHT, 'A')),
//            'E' to Pair(Rule(1, Direction.LEFT, 'F'),
//                    Rule(0, Direction.LEFT, 'D')),
//            'F' to Pair(Rule(1, Direction.RIGHT, 'A'),
//                    Rule(0, Direction.LEFT, 'E')))

    val rules = hashMapOf<State, Pair<Rule, Rule>>(
            'A' to Pair(Rule(1, Direction.RIGHT, 'B'),
                    Rule(0, Direction.LEFT, 'B')),
            'B' to Pair(Rule(0, Direction.RIGHT, 'C'),
                    Rule(1, Direction.LEFT, 'B')),
            'C' to Pair(Rule(1, Direction.RIGHT, 'D'),
                    Rule(0, Direction.LEFT, 'A')),
            'D' to Pair(Rule(1, Direction.LEFT, 'E'),
                    Rule(1, Direction.LEFT, 'F')),
            'E' to Pair(Rule(1, Direction.LEFT, 'A'),
                    Rule(0, Direction.LEFT, 'D')),
            'F' to Pair(Rule(1, Direction.RIGHT, 'A'),
                    Rule(1, Direction.LEFT, 'E')))

    val memory = mutableListOf(0)
    var currState: State = 'A'
    var currPosition = 0
    for (i in 1..12629077) {
        val rules = rules[currState]!!
        val rule = if (memory[currPosition] == 0) rules.first else rules.second
        memory[currPosition] = rule.value
        when (rule.direction) {
            Direction.LEFT -> currPosition--
            Direction.RIGHT -> currPosition++
        }
        if (currPosition < 0) {
            currPosition = 0
            memory.add(0, 0)
        } else if (currPosition == memory.size) {
            memory.add(0)
        }
        currState = rule.nextState
    }
    println(memory.sum())
}