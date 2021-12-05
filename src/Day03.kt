fun main() {
    fun List<String>.binaryComparisonReduce(compare: (Int, Int) -> Boolean): String {
        var result = ""
        for (j in first().indices) {
            var countZero = 0
            var countOne = 0
            for (i in indices) {
                when(this[i][j]) {
                    '0' -> countZero += 1
                    '1' -> countOne += 1
                }
            }
            result += if (compare(countZero, countOne)) '0' else '1'
        }
        return result
    }

    fun part1(input: List<String>): Int {
        val gammaRate = input.binaryComparisonReduce { a, b -> a > b }.toInt(2)
        val epsilonRate = input.binaryComparisonReduce { a, b -> a < b }.toInt(2)
        return gammaRate * epsilonRate
    }

    fun doRecursiveFilter(list: List<String>, condition: String, compare: (Int, Int) -> Boolean, index: Int): String {
        val filtered = list.filter { it[index] == condition[index] }
        return filtered.singleOrNull()
            ?: doRecursiveFilter(filtered, filtered.binaryComparisonReduce(compare), compare, index + 1)
    }

    fun recursiveFilter(list: List<String>, compare: (Int, Int) -> Boolean) =
        doRecursiveFilter(list, list.binaryComparisonReduce(compare), compare, 0)

    fun part2(input: List<String>): Int {
        val oxygenGeneratorRating = recursiveFilter(input) { a, b -> a > b }
            .toInt(2)
        val co2ScrubberRating = recursiveFilter(input) { a, b -> a <= b }
            .toInt(2)
        return oxygenGeneratorRating * co2ScrubberRating
    }

    val testInput = readInput("Day03_test")
    val input = readInput("Day03")

    check(part1(testInput) == 198)
    println(part1(input))

    check(part2(testInput) == 230)
    println(part2(input))
}
