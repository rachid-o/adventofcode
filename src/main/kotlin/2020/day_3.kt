package `2020`

import java.io.File


fun main() {
    val inputFilename = "day_3.txt"
    println("Reading file $resourcePath/$inputFilename")
    val lines = File("$resourcePath/$inputFilename")
            .readLines()

    println("file size:  ${lines.size}")

    part1(lines)

    part2(lines)
}

private fun part1(lines: List<String>) {
    println("Part 1: ")
    val nrOfTrees = countTrees(lines, 3, 1)
    println("nrOfTrees: $nrOfTrees \n")
}

private fun part2(lines: List<String>) {
    println("Part 2: ")
    val trees = listOf(
     countTrees(lines, 1, 1),
     countTrees(lines, 3, 1),
     countTrees(lines, 5, 1),
     countTrees(lines, 7, 1),
     countTrees(lines, 1, 2)
    )

    val answer = trees
        .map { it.toLong() }
        .reduce { acc, i -> i * acc }

    println("multiplied together: $answer")
}

private fun countTrees(lines: List<String>, right: Int, down: Int): Int {
    var row = 0
    var col = 0
    var nrOfTrees = 0
    while ( (row+ down) < lines.size) {
        row += down
        col += right
        val line = lines[row]
        if (col >= line.length) {
            col -= line.length
        }
//        println("$line \t $row.$col\t - ${line[col]}")
        if (line[col] == '#') {
            nrOfTrees++
        }
    }
    println("$right, $down = $nrOfTrees")
    return nrOfTrees
}
