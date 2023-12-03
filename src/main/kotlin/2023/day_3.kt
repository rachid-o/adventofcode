package `2023`

import utils.Grid
import java.io.File

fun main() {
    val inputFilename = "day_3.txt"
    println("Reading file $resourcePath/$inputFilename")
    val input = File(resourcePath, inputFilename)
        .readLines()
        .map { row -> row.toCharArray().toList() }

    val schematic = Grid(input)
//    println(schematic)
    val answer1 = part1(schematic)
    println("Answer 1: $answer1")

    check(answer1 == 544_664)
}

private fun part1(schematic: Grid<Char>): Int {
    val numbers = mutableListOf<Int>()
    var currentNumber = ""
    var isValidPart = false
    schematic.getAll().forEach { (point, value) ->
        if(value.isDigit()) {
            currentNumber += value
            val neighbours = schematic.getAllNeighbors(point).map { schematic.getValue(it) }
            if(neighbours.any { !it.isDigit() && it != '.' }) {
                isValidPart = true
            }
        }
        if((point.col == schematic.getWidth()-1 || !value.isDigit()) && currentNumber.isNotEmpty()) {
            if(isValidPart) {
                numbers.add(currentNumber.toInt())
            }
            currentNumber = ""
            isValidPart = false
        }
    }
    println("${numbers.size} parts: $numbers")
    return numbers.sum()
}
