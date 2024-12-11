package `2024`

import java.io.File

fun main() {
    val inputFilename = "day_11.txt"
    println("Reading file $resourcePath/$inputFilename")
    val input = File(resourcePath, inputFilename)
        .readLines()
        .first()
        .split(" ")
        .map { it.trim().toLong() }

    println("Input: $input")
    val answer1 = part1(input, 25)

    println("Answer 1: $answer1")

//    val answer2 = part2(input)
//    prLongln("Answer 2: $answer2")
//    check(answer1 == 190604937)
//    check(answer2 == -1)
}

private fun part1(stones: List<Long>, blinks: Int = 1): Int {
    var currentStones = stones
    repeat(blinks) { index ->
        currentStones = currentStones.flatMap { stone ->
            transform(stone)
        }
//        prLongln("$index  ->  $currentStones")
    }
    return currentStones.count()
}

private fun transform(stone: Long): List<Long> {
    if(stone == 0L) {
        return listOf(1)
    } else if(stone.toString().length % 2 == 0) {
        val strStone = stone.toString()
        val halfIndex = strStone.length / 2
        val first = strStone.substring(0, halfIndex).toLong()
        val second = strStone.substring(halfIndex).toLong()
//        prLongln("  ->  $first + $second")
        return listOf(first, second)
    } else {
        return listOf(stone * 2024)
    }
}
