package `2024`

import `2024`.Direction.left
import utils.Grid
import java.io.File

fun main() {
    val inputFilename = "day_4.txt"
    println("Reading file $resourcePath/$inputFilename")
    val input = File(resourcePath, inputFilename)
        .readLines()
        .map { row -> row.toCharArray().toList() }

    val puzzle = Grid(input)

//    val letters = puzzle.getAll().filterNot { it.value == '.' }.count()
//    println("letters: $letters")

    val answer1 = part1(puzzle)
    println("Answer 1: $answer1")

//    val answer2 = part2(rules, updates)
//    println("Answer 2: $answer2")
//    check(answer1 == 18)
    check(answer1 == 2401)
//    check(answer2 == 6257)
}

val word = "XMAS"

private fun part1(puzzle: Grid<Char>): Int {
//    println("puzzle: \n$puzzle")

    val first = puzzle.getAll().filter { it.value == word[0] }.map { it.key } // All potential beginnings
    return  first.sumOf { countWords(puzzle, it) }
}

private fun countWords(puzzle: Grid<Char>, pos: Grid.Point): Int {

    val dirs = Direction.entries.filter { dir ->
        val neighbor = dir.next(pos)
        if(neighbor !in puzzle.getAll()) {
            return@filter false
        }
        val nextVal = puzzle.getValue(neighbor)
        nextVal == 'M'
    }

    return dirs.count { dir ->
        var nextPos = pos
        for(c in word.toCharArray(1)) {
            nextPos = dir.next(nextPos)
            if(nextPos in puzzle.getAll() && puzzle.getValue(nextPos) == c) {
                // continue
            } else {
                return@count false
            }
        }
        true // We found the word!
    }

}

private fun Direction.next(current: Grid.Point): Grid.Point {
    return when(this) {
        left -> current.copy(col = current.col - 1)
        Direction.right -> current.copy(col = current.col + 1)
        Direction.up -> current.copy(row = current.row - 1)
        Direction.down -> current.copy(row = current.row + 1)
        Direction.upright -> current.copy(row = current.row - 1, col = current.col + 1)
        Direction.upleft -> current.copy(row = current.row - 1, col = current.col - 1)
        Direction.downright -> current.copy(row = current.row + 1, col = current.col + 1)
        Direction.downleft -> current.copy(row = current.row + 1, col = current.col - 1)
    }
}

private enum class Direction {
    left,
    right,
    up,
    down,
    upleft,
    upright,
    downleft,
    downright;

}
