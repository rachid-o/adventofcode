package `2020`

import java.io.File


fun main() {
    val inputFilename = "day_10.txt"
    val data = parseFile("$resourcePath/$inputFilename")
    println("nr of numbers in file:  ${data.size}")

    println("Part 1 answer: ${part1(data)}")
    println("Part 2 answer: ${part2(data)}")
}

internal fun day10part2(input: List<Int>) = part2(input)

private fun part2(input: List<Int>): Long {
    println("Part 2: ")
    val reachables = mutableMapOf<Int, Long>(0 to 1)
    var lastAdapterValue: Long = 0
    input.forEachIndexed { index, adapter ->
        var adapterValue: Long = 0
        if(reachables.containsKey(adapter - 1))
            adapterValue += reachables.getValue(adapter-1)
        if(reachables.containsKey(adapter - 2))
            adapterValue += reachables.getValue(adapter-2)
        if(reachables.containsKey(adapter - 3))
            adapterValue += reachables.getValue(adapter-3)

        reachables[adapter] = adapterValue
        lastAdapterValue = adapterValue
    }
    return lastAdapterValue
}

private fun part1(input: List<Int>): Int {
    println("Part 1: ")
    var ones = 0
    var threes = 1
    var currAdapter = 0
    input.forEach {
        val diff = it - currAdapter
        when {
            diff == 1 -> {
                ones++
            }
            diff <= 3 -> {
                threes++
            }
            else -> {
                // Skip this adapter
            }
        }
        currAdapter = it
    }

    val answer = ones * threes
    println("$ones  *  $threes  =  $answer")
    return answer
}


private fun parseFile(inputFile: String) = File(inputFile)
        .readLines()
        .map { it.toInt() }
        .sorted()
