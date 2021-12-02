fun main() {
    fun part1(input: List<String>): Int {
        // this approach is sort of neat, but a naive test shows it's about 3x slower than what's below
        /*
        return input.map { it.toInt() }
            .windowed(2)
            .count { it.last() > it.first() }
         */
        var increasing = 0
        for (i in 1 until input.size) {
            if (input[i].toInt() > input[i - 1].toInt()) ++increasing
        }
        return increasing
    }

    fun part2(input: List<String>): Int {
        // no way I'm not using windowed() for this part...
        val windowSums = input.map { it.toInt() }
            .windowed(size = 3, partialWindows = false)
            .map { it.sum() }
        var increasing = 0
        for (i in 1 until windowSums.size) {
            if (windowSums[i] > windowSums[i - 1]) ++increasing
        }
        return increasing
    }

    val testInput = readInput("Day01_test")
    val input = readInput("Day01")

    check(part1(testInput) == 7)
    println(part1(input))

    check(part2(testInput) == 5)
    println(part2(input))
}
