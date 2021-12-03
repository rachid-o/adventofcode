package `2021`

import java.io.File

fun main() {
    val inputFilename = "day_1.txt"
    println("Reading file $resourcePath/$inputFilename")
    val report = File(resourcePath, inputFilename)
        .readLines()
        .map { it.toInt() }

    println("Report size:  ${report.size}")

    println("Answer 1: " + part1(report))
    println("Answer 2: " + part2(report))
}

private fun part1(report: List<Int>) = countIncreases(report)

private fun part2(report: List<Int>): Int {
    val windows = report.windowed(size = 3)
        .map { it.sum() }
    return countIncreases(windows)
}

private fun countIncreases(report: List<Int>) =
    report.windowed(size = 2)
        .count { (prev, current) -> current > prev }
