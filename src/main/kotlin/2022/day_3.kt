package `2022`

import java.io.File

const val alphabet = "abcdefghijklmnopqrstuvwxyz"
val prios1 = alphabet.mapIndexed { index, c ->
    c to index+1
}.toMap()
val prios2 = alphabet.mapIndexed { index, c ->
    c.uppercase().toCharArray()[0] to index+27
}.toMap()
val prios = prios1 + prios2

fun main() {
    val inputFilename = "day_3.txt"
    println("Reading file $resourcePath/$inputFilename")
    println("prios: $prios")

    val input = File(resourcePath, inputFilename)
        .readLines()
        .map {
            it.map { c -> prios.getValue(c) }
        }
    val input1 = input.map {
            val half = it.size / 2
            it.subList(0, half) to  it.subList(half, it.size)
        }

    val answer1 = part1(input1)
    println("Answer 1: $answer1")

    val answer2 = part2(input)
    println("Answer 2: $answer2")
    check(answer1 == 8105)
    check(answer2 == 2363)
}

private fun part1(input: List<Pair<List<Int>, List<Int>>>) = input.sumOf { rucksack ->
    rucksack.first.find { c -> rucksack.second.contains(c) }!!
}

private fun part2(input: List<List<Int>>) = input
    .windowed(3, 3)
    .sumOf {
        getBadge(it)
    }

fun getBadge(rugsacks: List<List<Int>>): Int {
    val potentialBadges = rugsacks.flatten().toSet()
    return potentialBadges.find { pb -> rugsacks.all { it.contains(pb) } }!!
}
