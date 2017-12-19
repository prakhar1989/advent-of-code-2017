fun main(args: Array<String>) {
    val aFactor = 16807L
    val bFactor = 48271L
    val MOD = 2147483647L
    val mask = 65535L // 2 ^ 16 - 1

    var a = 512L
    var b = 191L
    var counter = 0

    for (i in 0 until 5_000_000) {
        do {
            a = (a * aFactor) % MOD
        } while (a % 4 != 0L)

        do {
            b = (b * bFactor) % MOD
        } while (b % 8 != 0L)

        if (a and(mask) == b and(mask)) {
            counter++
        }
    }

    println(counter)
}