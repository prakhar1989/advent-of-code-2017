import kotlin.test.assertEquals

private enum class Direction { LEFT, RIGHT }
private enum class State {A, B, C, D, E, F}

private data class Rule(
    val value: Int,
    val direction: Direction,
    val nextState: State
)

fun main(args: Array<String>) {
    val rules = hashMapOf<State, Pair<Rule, Rule>>(
            State.A to Pair(Rule(1, Direction.RIGHT, State.B),
                    Rule(0, Direction.LEFT, State.B)),
            State.B to Pair(Rule(0, Direction.RIGHT, State.C),
                    Rule(1, Direction.LEFT, State.B)),
            State.C to Pair(Rule(1, Direction.RIGHT, State.D),
                    Rule(0, Direction.LEFT, State.A)),
            State.D to Pair(Rule(1, Direction.LEFT, State.E),
                    Rule(1, Direction.LEFT, State.F)),
            State.E to Pair(Rule(1, Direction.LEFT, State.A),
                    Rule(0, Direction.LEFT, State.D)),
            State.F to Pair(Rule(1, Direction.RIGHT, State.A),
                    Rule(1, Direction.LEFT, State.E)))

    val memory = mutableListOf(0)
    var currState: State = State.A
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
    assertEquals(3745, memory.sum())
}