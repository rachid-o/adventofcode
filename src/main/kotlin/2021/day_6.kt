package `2021`

import java.io.File

fun main() {
    val inputFilename = "day_6.txt"
    println("Reading file $resourcePath/$inputFilename")
    val input = File(resourcePath, inputFilename)
        .readText()
        .split(",")
        .map(String::toInt)
    println("input: $input")

    println("Answer 1: " + part1(input, 80))
    println("Answer 2: " + part2(input, 256))
}

private fun part1(input: List<Int>, days: Int): Int {
    val fishes = input.toMutableList()
    var newFishes = 0

    repeat(days) {
        fishes.forEachIndexed { index, fish ->
            if (fish > 0) {
                fishes[index]--
            } else {
                fishes[index] = 6
                newFishes++
            }
        }
        for (i in 1..newFishes) {
            fishes.add(8)
        }
        newFishes = 0
    }
    val answer = fishes.size
    println("Answer: $answer")
    check(answer == 350149)
    return answer
}

private fun part2(input: List<Int>, days: Int): Long {
    var fishes = input.groupBy { it }.mapValues { (_, v) -> v.size.toLong() }
    println("Fishes: $fishes")

    (1..days).forEach { day ->
        val newFishes = mutableMapOf<Int, Long>()
        fishes.entries.forEach { (fish, amount) ->
            if (fish > 0) {
                newFishes[fish - 1] = newFishes.getOrDefault(fish - 1, 0) + amount
            } else {
                newFishes[6] = newFishes.getOrDefault(6, 0) + amount
                newFishes[8] = amount
            }
        }
        fishes = newFishes
    }
    val answer: Long = fishes.values.sum()
    println("Answer: $answer")
//    check(answer == 1590327954513L)
    return answer
}
