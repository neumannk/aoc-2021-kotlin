fun main() {
    fun part1(input: List<String>): Int {
        var fish = input.first().split(",").map { it.toInt() }
        repeat(80) {
            fish = fish.flatMap { it.nextDay() }
        }
        return fish.size
    }

    fun part2(input: List<String>): Long {
        val initialFishByAge = input.first().split(",").map { it.toInt() }
            .groupingBy { it }
            .eachCount()
        var countByAge = List(9) { index -> (initialFishByAge[index] ?: 0).toLong() }

        repeat(256) {
            countByAge = List(9) { age ->
                when (age) {
                    6 -> countByAge[7] + countByAge[0]
                    8 -> countByAge[0]
                    else -> countByAge[age + 1]
                }
            }
        }
        return countByAge.sum()
    }

    val testInput = readInput("Day06_test")
    val input = readInput("Day06")

    check(part1(testInput) == 5934)
    println(part1(input))

    check(part2(testInput) == 26984457539)
    println(part2(input))
}

fun Int.nextDay(): List<Int> {
    if (this == 0) return listOf(6, 8)
    return listOf(this - 1)
}
