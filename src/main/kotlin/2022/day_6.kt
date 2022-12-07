package `2022`

import java.io.File

fun main() {
    val inputFilename = "day_6.txt"
    println("Reading file $resourcePath/$inputFilename")
    val input = File(resourcePath, inputFilename)
        .readLines()

    val input1 = input[0]

    println("input: $input1")


    val answer1 = part1(input1, 4)
    println("Answer 1: $answer1")

    val answer2 = part1(input1, 14)
    println("Answer 2: $answer2")
}

private fun part1(input: String, markerLength: Int): Int {
    var chars = 0
    input.windowed(markerLength)
        .forEach {
            if(it.toCharArray().toSet().size == markerLength) {
                return chars+markerLength;
            }
            chars++
        }
    throw IllegalStateException("Not found")
}

