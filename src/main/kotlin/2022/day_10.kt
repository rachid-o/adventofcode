package `2022`

import java.io.File

fun main() {
    val inputFilename = "day_10.txt"
    println("Reading file $resourcePath/$inputFilename")
    val input = File(resourcePath, inputFilename)
        .readLines()

    println("input: $input")

    val answer1 = part1(input)
    println("Answer 1: $answer1")
    val answer2 = part2(input)
    println("Answer 2: \n$answer2")
}

private fun part1(input: List<String>): Int {
    val cycles = calcCycles(input)
    return (20..220 step 40).sumOf { signalCycle ->
        signalCycle * cycles.get(signalCycle - 1)
    }
}

private fun part2(input: List<String>) = calcCycles(input)
    .chunked(40)
    .map { subCycle ->
        subCycle.mapIndexed { index, x ->
            if (index in x - 1..x + 1) {
                '#'
            } else {
                '.'
            }
        }.joinToString("")
    }.joinToString("\n")


private fun calcCycles(input: List<String>): List<Int> {
    var x = 1
    val cycles = mutableListOf<Int>()
    input.forEach { ins ->
        cycles.add(x)
        if (ins != "noop") {
            cycles.add(x)
            val value = ins.split(" ")[1].toInt()
            x += value
        }
    }
    return cycles
}
