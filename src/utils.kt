/**
 * Returns a list of combinations of size 2
 * of a list.
 *
 * e.g. allPairs(listOf(1, 2, 3)) ->
 * [(1, 1), (1, 2), (1, 3) ... (3, 3)]
 */
fun <T>allPairs(xs: List<T>): List<Pair<T, T>> {
    return xs.map { xs.map { ys -> Pair(it, ys) } }
            .flatten()
}