fun main() {
    fun part1(input: List<String>): Int {
        var position = 0
        var depth = 0
        input.forEach {
            val (direction, amountStr) = it.split(' ')
            val amount = amountStr.toInt()
            when(direction) {
                "forward" -> position += amount
                "down" -> depth += amount
                "up" -> depth -= amount
            }
        }
        return position * depth
    }

    fun part2(input: List<String>): Int {
        var position = 0
        var depth = 0
        var aim = 0
        input.forEach {
            val (direction, amountStr) = it.split(' ')
            val amount = amountStr.toInt()
            when(direction) {
                "forward" -> {
                    position += amount
                    depth += aim * amount
                }
                "down" -> aim += amount
                "up" -> aim -= amount
            }
        }
        return position * depth
    }

    val testInput = readInput("Day02_test")
    val input = readInput("Day02")

    check(part1(testInput) == 150)
    println(part1(input))

    check(part2(testInput) == 900)
    println(part2(input))
}
