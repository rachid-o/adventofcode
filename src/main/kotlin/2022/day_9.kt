package `2022`

import java.io.File

fun main() {
    val inputFilename = "day_9.txt"
    println("Reading file $resourcePath/$inputFilename")
    val input = File(resourcePath, inputFilename)
        .readLines()

    println("input: \n$input")

//    val answer1 = part1(input)
//    println("Answer 1: $answer1")

//    val answer2 = part2(input)
//    println("Answer 2: $answer2")
}

private fun part1(input: List<String>) {
    TODO()
}
