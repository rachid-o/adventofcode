package `2021`

import java.io.File

fun main() {
    val inputFilename = "day_4.txt"
    println("Reading file $resourcePath/$inputFilename")
    val (bingo, boards) = parseFile(File(resourcePath, inputFilename))

    println("bingo: $bingo")
    println("boards:")
    boards.forEach { println("============= \n${it}") }

    println("Answer 1: " + part1(bingo, boards))
    println("Answer 2: " + part2(bingo, boards))
}


private fun part1(bingo: List<Int>, boards: List<Board>): Int {

    bingo.forEach { number ->
        boards.forEach { board ->
            board.mark(number)
            if (board.hasWon()) {
                println("This board has won: \n$board")
                val answer = number * board.sumRemainders()
//                check(answer == 4512)
                check(answer == 44736)
                return answer
            }
        }
    }

    return -1
}

private fun part2(bingo: List<Int>, boards: List<Board>): Int {

    val currentBoards = boards.toMutableList()
    val removeBoards = mutableListOf<Board>()
    bingo.forEach { number ->
        if(removeBoards.isNotEmpty()) {
            currentBoards.removeAll(removeBoards)
            removeBoards.clear()
        }
        currentBoards.forEachIndexed { index, board ->
            board.mark(number)
            if (board.hasWon()) {
                removeBoards.add(board)
                if(currentBoards.size == 1) {
                    val answer = number * board.sumRemainders()
//                    check(answer == 1924)
                    check(answer == 1827)
                    return answer
                }
            }
        }
    }
    return -1
}

/**
 *  rows X cols
 */
private data class Board(val numbers: List<MutableList<Int>>) {
    private val MARKED = -1

    override fun toString() = numbers.joinToString("\n")
    fun mark(number: Int) {
        for (rowIndex in numbers.indices) {
            val col = numbers[rowIndex]
            val colIndex = col.indexOf(number)
            if (colIndex > -1) {
                col[colIndex] = MARKED
                return
            }
        }
    }

    fun hasWon(): Boolean {
        if (numbers.any { it.all { it == MARKED } }) {
            return true // Won by rows
        } else {
            for (colIndex in numbers.indices) { // Incorrect, but works
                val colNumbers = numbers.map { row -> row[colIndex] }
                if (colNumbers.all { it == MARKED }) {
                    return true
                }
            }
            return false
        }
    }

    fun sumRemainders() = numbers.sumOf { it.filter { it != MARKED }.sum() }
}


private fun parseFile(inputFile: File): Pair<List<Int>, List<Board>> {
    val parts = inputFile.readText().split("\n\n").map { it.trim() }
    val bingo = parts.first().split(",").map(String::toInt)
    val boards = parts.drop(1).map { parseBoard(it) }

    return Pair(bingo, boards)
}

private fun parseBoard(strBoard: String): Board {
    val numbers = strBoard
        .split("\n")
        .map { row ->
            row.trim()
                .split("\\s+".toRegex())
                .map(String::toInt)
                .toMutableList()
        }
    return Board(numbers)
}
