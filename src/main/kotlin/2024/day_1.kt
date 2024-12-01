package `2024`

import java.io.File

fun main() {
    val inputFilename = "day_1.txt"
    println("Reading file $resourcePath/$inputFilename")
    val first = mutableListOf<Int>()
    val second = mutableListOf<Int>()

    File(resourcePath, inputFilename)
        .readLines()
        .map {
            val (a, b) = it.split("   ").map { it.toInt() }
            first.add(a)
            second.add(b)
        }
    first.sort()
    second.sort()

    val answer1 = part1(first, second)
    println("Answer 1: $answer1")

    val answer2 = part2(first, second)
    println("Answer 2: $answer2")
    check(answer1 == 936063)
    check(answer2 == 23150395)
}

private fun part1(first: List<Int>, second: List<Int>) = first.mapIndexed { index, a ->
    val b = second[index]
    val distance = Math.abs(a - b)
    println("$a - $b = $distance")
    distance
}.sum()

private fun part2(first: List<Int>, second: List<Int>) = first.mapIndexed { index, a ->
    val count = second.count { it == a }
    val sim = a * count
    println("$a * $count = $sim")
    sim
}.sum()
