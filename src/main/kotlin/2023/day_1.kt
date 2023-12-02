package `2023`

import java.io.File

fun main() {
    val inputFilename = "day_1.txt"
    println("Reading file $resourcePath/$inputFilename")
    val input = File(resourcePath, inputFilename)
        .readLines()
    println("input: $input")

    val answer1 = part1(input)
    println("Answer 1: $answer1")

    val answer2 = part2(input)
    println("Answer 2: $answer2")
    check(answer1 == 54951)
    check(answer2 == 55218)
}

private fun part1(lines: List<String>) =
    lines.sumOf { line ->
        val digits = line.filter { it.isDigit() }
        (digits.first().toString() + digits.last().toString()).toInt()
    }

private val digitMap = mapOf(
    "one" to "one1one",
    "two" to "tw2wo",
    "three" to "thre3hree",
    "four" to "fou4our",
    "five" to "fiv5ive",
    "six" to "si6ix",
    "seven" to "seve7even",
    "eight" to "eigh8ight",
    "nine" to "nin9ine"
)

private fun part2(lines: List<String>): Int {
    val newLines = lines.map { line ->
        var newLine = line
        digitMap.entries.forEach { (word, digit) ->
            newLine = newLine.replace(word, digit)
        }
        newLine
    }
    return part1(newLines)
}
