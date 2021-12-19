fun main() {
    fun part1(input: List<String>): Int {
        val numbers = input.first().split(",").map { it.toInt() }
        val boards = input.parseBoards()

        numbers.forEach { number ->
            boards.forEach { board ->
                board.markAndCheckWinner(number)?.let { winningScore ->
                    return winningScore
                }
            }
        }
        throw RuntimeException("No winner")
    }

    fun part2(input: List<String>): Int {
        val numbers = input.first().split(",").map { it.toInt() }
        var boards = input.parseBoards()

        numbers.forEach { number ->
            boards = boards.filterNot { board ->
                board.markAndCheckWinner(number)?.let { winningScore ->
                    if (boards.size == 1) return winningScore
                    true
                } ?: false
            }
        }
        throw RuntimeException("No winner")
    }

    val testInput = readInput("Day04_test")
    val input = readInput("Day04")

    check(part1(testInput) == 4512)
    println(part1(input))

    check(part2(testInput) == 1924)
    println(part2(input))
}

fun List<String>.parseBoards(): List<Board> {
    val boards = mutableListOf<Board>()
    var rows = mutableListOf<Map<Int, Square>>()
    for (i in 1 until size) {
        if (this[i].isEmpty()) continue
        rows.add(
            this[i].trim().split(whitespace)
                .map { it.toInt() }
                .mapIndexed { column, value ->
                    value to Square(rows.size, column)
                }.toMap()
        )

        if (rows.size == boardSize) {
            boards.add(
                Board(
                    rows.reduce { acc, row ->
                        acc.plus(row)
                    }.toMutableMap()
                )
            )
            rows = mutableListOf()
        }
    }
    return boards
}

val whitespace = """\s+""".toRegex()

data class Square(
    val row: Int,
    val column: Int,
)

const val boardSize = 5

data class Board(
    val unmarked: MutableMap<Int, Square>,
    val marked: MutableMap<Int, Square> = mutableMapOf(),
    val markedPerRow: MutableList<Int> = MutableList(boardSize) { 0 },
    val markedPerColumn: MutableList<Int> = MutableList(boardSize) { 0 },
) {

    /** If the value exists on the board, mark it. If the board is a winner, return its score, else null. */
    fun markAndCheckWinner(value: Int): Int? {
        val square = unmarked.remove(value) ?: return null
        marked[value] = square
        markedPerRow[square.row] += 1
        if (markedPerRow[square.row] == boardSize) {
            return score(value)
        }
        markedPerColumn[square.column] += 1
        if (markedPerColumn[square.column] == boardSize) {
            return score(value)
        }
        return null
    }

    private fun score(value: Int) = unmarked.keys.sum() * value
}
