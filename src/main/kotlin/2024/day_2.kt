package `2024`

import java.io.File

fun main() {
    val inputFilename = "day_2.txt"
    println("Reading file $resourcePath/$inputFilename")
    val input = File(resourcePath, inputFilename)
        .readLines()
    val reports = parseInput(input)

    val answer1 = part1(reports)
    println("Answer 1: $answer1")

    val answer2 = part2(reports)
    println("Answer 2: $answer2")
    check(answer1 == 472)
    check(answer2 == 520)
}

private fun parseInput(input: List<String>) = input
    .map { it.split(" ").map { it.trim().toInt() } }

private fun part1(reports: List<List<Int>>) = reports
    .count { isSafe(it) }

private fun part2(reports: List<List<Int>>) = reports
    .count { report ->
        var isSafe = isSafe(report)
        if (!isSafe) {
            isSafe = report.indices.any { index ->
                val reportMinOneLevel = report.toMutableList()
                reportMinOneLevel.removeAt(index)
                if(isSafe(reportMinOneLevel)) {
                    return@any true
                }
                false
            }
        }
        isSafe
    }

private fun isSafe(report: List<Int>): Boolean {
    if (report[0] == report[1])
        return false
    val increasing = report[0] > report[1]

    report.windowed(2, 1)
        .forEach { levels ->
            val diff = if (increasing) {
                levels[0] - levels[1]
            } else {
                levels[1] - levels[0]
            }
            if (diff !in 1..3) {
                return false
            }
        }
    return true
}
