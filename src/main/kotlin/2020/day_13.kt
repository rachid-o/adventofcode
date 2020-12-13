package `2020`

import java.io.File


fun main() {
    val inputFilename = "day_13.txt"
    val input = parseFile("$resourcePath/$inputFilename")
    println("Earliest bus:  ${input.first}")
    println("All bus ID's:  ${input.second} \n")

    val answer = part1(input.first, input.second)
    println("Part 1 answer is: $answer")

    val answer2 = part2(input.second)
    println("Part 2 answer is: $answer2")
}

private fun part2(busIDs: List<Int>): Long {
    println("Part 2: ")
    val busIdWithDelta = busIDs.mapIndexed { index, busId ->
        index to busId.toLong()
    }
        .filter { it.second > 0 }

    var stepSize = busIdWithDelta.first().second
    var time = 0L
    busIdWithDelta.drop(1).forEach { (delta, busId) ->
        while ((time + delta) % busId != 0L) {
            time += stepSize
        }
        stepSize *= busId
    }
    return time
}


private fun part1(earliest: Int, busIDs: List<Int>): Int {
    println("Part 1: ")
    val ids = busIDs.filter { it > 0 }.sorted()
    val max = ids.first()
    for (time in earliest..earliest + max) {
        val busID = ids.find { time % it == 0 }
        if (busID != null) {
            val waitTime = time - earliest
            println("busID: $busID - waitTime $waitTime")
            return busID * waitTime
        }
    }
    return -1
}


private fun parseFile(inputFile: String): Pair<Int, List<Int>> {

    val lines = File(inputFile).readLines()
    val earliest = lines[0].toInt()
    val busIDs = lines[1]
        .split(",")
        .map { if (it == "x") -1 else it.toInt() }
    return Pair(earliest, busIDs)
}
