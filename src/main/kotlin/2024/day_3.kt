package `2024`

import java.io.File

private val pattern = """mul\((\d+),(\d+)\)""".toRegex()

fun main() {
    val inputFilename = "day_3.txt"
    println("Reading file $resourcePath/$inputFilename")
    val input = File(resourcePath, inputFilename)
        .readLines()

    val answer1 = part1(input)
    println("Answer 1: $answer1")

//    val answer2 = part2(input)
//    println("Answer 2: $answer2")
//    check(answer1 == 190604937)
//    check(answer2 == -1)
}

private fun part1(input: List<String>) = input.sumOf { line ->
    pattern.findAll(line)
        .map { mul ->
            val (a, b) = mul.destructured
            println("$a * $b = ${a.toInt() * b.toInt()}")
            a.toInt() * b.toInt()
        }.sum()
}