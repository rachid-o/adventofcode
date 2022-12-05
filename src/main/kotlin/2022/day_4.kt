package `2022`

import java.io.File

fun main() {
    val inputFilename = "day_4.txt"
    println("Reading file $resourcePath/$inputFilename")
    val input = File(resourcePath, inputFilename)
        .readLines()
        .map { pair ->
            val (first, second) = pair.split(",")
                .map { strRange ->
                    val array = strRange.split("-")
                    val range = array[0].toInt()..array[1].toInt()
                    range.map { it }.toSet()
                }
            first to second
        }

    val answer1 = part1(input)
    println("Answer 1: $answer1")

    val answer2 = part2(input)
    println("Answer 2: $answer2")
}

private fun part1(input: List<Pair<Set<Int>, Set<Int>>>): Int {
    return input.count { (first, second) ->
        first.containsAll(second)  || second.containsAll(first)
    }
}

private fun part2(input: List<Pair<Set<Int>, Set<Int>>>): Int {
    return input.count { (first, second) ->
        first.any { second.contains(it) } || second.any { first.contains(it) }
    }
}
