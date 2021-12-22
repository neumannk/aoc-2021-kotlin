import kotlin.math.abs
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.round

fun main() {
    fun part1(input: List<String>): Int {
        val positions = input.first().split(",").map { it.toInt() }
        val idealPosition = round(positions.median()).toInt()
        return positions.sumOf {
            abs(it - idealPosition)
        }
    }

    fun part2(input: List<String>): Int {
        val positions = input.first().split(",").map { it.toInt() }
        // horrible brute force solution, but the math here goes a bit over my head :/
        val min = positions.minOrNull()!!
        val max = positions.maxOrNull()!!
        var cheapest = Int.MAX_VALUE
        for (candidateIdealPosition in min..max) {
            cheapest = min(
                cheapest,
                positions.sumOf {
                    abs(it - candidateIdealPosition).termial()
                }
            )
        }
        return cheapest
    }

    val testInput = readInput("Day07_test")
    val input = readInput("Day07")

    check(part1(testInput) == 37)
    println(part1(input))

    check(part2(testInput) == 168)
    println(part2(input))
}

fun Iterable<Int>.median(): Double {
    val sorted = this.sorted()
    return if (sorted.size % 2 == 0) {
        (sorted[sorted.size / 2] + sorted[(sorted.size / 2) - 1]) / 2.0
    } else {
        sorted[(sorted.size / 2)].toDouble()
    }
}

fun Int.termial() = ((this.toDouble().pow(2) + this) / 2.0).toInt()