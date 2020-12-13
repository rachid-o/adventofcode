package `2020`

import java.io.File


fun main() {
    val inputFilename = "day_10.txt"
    val data = parseFile("$resourcePath/$inputFilename")
    println("nr of numbers in file:  ${data.size}")

    part1(data)

//    part2(data)

}

private fun part1(input: List<Int>) {
    println("Part 1: ")
    var ones = 0
    var threes = 1
    var currAdapter = 0
    input.forEach {
        val diff = it - currAdapter
        if(diff == 1) {
            ones++
        } else if(diff <= 3) {
            threes++
        } else {
            // Skip this adapter
        }
        currAdapter = it
    }
    val answer = ones * threes
    println("$ones  *  $threes  =  $answer")
}



private fun parseFile(inputFile: String) = File(inputFile)
        .readLines()
        .map { it.toInt() }
        .sorted()
