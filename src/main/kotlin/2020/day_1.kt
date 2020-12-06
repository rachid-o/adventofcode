package `2020`

import java.io.File

fun main() {
    val inputFilename = "day_1.txt"
    println("Reading file $resourcePath/$inputFilename")
    val expenseReport = File("$resourcePath/$inputFilename")
        .readLines()
        .map { it.toInt() }

    println("expenseReport size:  ${expenseReport.size}")

    part1(expenseReport)

    part2(expenseReport)
}

private fun part1(expenseReport: List<Int>) {
    println("Part 1: ")
    expenseReport.forEach { first ->
        expenseReport.forEach { second ->
            if (first + second == 2020) {
                println("$first * $second = ${first * second}")
            }
        }
    }
}

private fun part2(expenseReport: List<Int>) {
    println("Part 2: ")
    expenseReport.forEach { first ->
        expenseReport.forEach { second ->
            expenseReport.forEach { third ->
                if (first + second + third == 2020) {
                    println("$first * $second * $third = ${first * second * third}")
                }
            }
        }
    }
}
