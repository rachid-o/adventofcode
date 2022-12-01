package `2022`

import java.io.File

fun main() {
    val inputFilename = "day_1.txt"
    println("Reading file $resourcePath/$inputFilename")
    val input = File(resourcePath, inputFilename)
        .readText().split("\n\n")
        .map {
            it.split("\n")
                .map { it.toInt() }
        }
    println("input: $input")

    println("Answer 1: " + part1(input))
    println("Answer 2: " + part2(input))
}

private fun part1(input: List<List<Int>>) = input.maxOfOrNull { it.sum() }

private fun part2(input: List<List<Int>>) = input.map { it.sum() }
        .sortedDescending()
        .subList(0, 3)
        .sum()
