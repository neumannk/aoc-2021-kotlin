import kotlin.math.max
import kotlin.math.min

fun main() {
    fun part1(input: List<String>) =
        input.map { it.parseLine() }
            .filterNot { it.isDiagonal }
            .flatMap { it.points }
            .groupingBy { it }
            .eachCount()
            .count { it.value > 1 }

    fun part2(input: List<String>) =
        input.map { it.parseLine() }
            .flatMap { it.points }
            .groupingBy { it }
            .eachCount()
            .count { it.value > 1 }

    val testInput = readInput("Day05_test")
    val input = readInput("Day05")

    check(part1(testInput) == 5)
    println(part1(input))

    check(part2(testInput) == 12)
    println(part2(input))
}

data class Point(val x: Int, val y: Int)
data class Line(val start: Point, val end: Point) {
    val isDiagonal: Boolean = start.x != end.x && start.y != end.y
    val points: Set<Point>
        get() {
            val points = mutableSetOf<Point>()

            if (isDiagonal) {
                val xIter = if (start.x <= end.x) {
                    (start.x..end.x)
                } else {
                    (start.x downTo end.x)
                }.iterator()

                val yIter = if (start.y <= end.y) {
                    (start.y..end.y)
                } else {
                    (start.y downTo end.y)
                }.iterator()

                while (xIter.hasNext() && yIter.hasNext()) {
                    points.add(Point(xIter.next(), yIter.next()))
                }
            } else {
                for (x in min(start.x, end.x)..max(start.x, end.x)) {
                    for (y in min(start.y, end.y)..max(start.y, end.y)) {
                        points.add(Point(x, y))
                    }
                }
            }

            return points
        }
}

fun String.parseLine(): Line {
    val (start, end) = this.split(" -> ").map {
        val (x, y) = it.split(",").map { it.toInt() }
        Point(x, y)
    }
    return Line(start, end)
}