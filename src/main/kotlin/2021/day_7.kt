package `2021`

import java.io.File
import kotlin.math.abs

fun main() {
    val inputFilename = "day_7.txt"
    println("Reading file $resourcePath/$inputFilename")
    val input = File(resourcePath, inputFilename)
        .readText()
        .split(",")
        .map(String::toInt)
    println("input(${input.size}): $input")
    println("Answer 1: " + part1(input))
    println("Answer 2: " + part2(input))
}

private fun part1(submarines: List<Int>): Int {
    val maxPosition = submarines.maxOrNull()!!
    val leastFuel = (0..maxPosition).fold(Integer.MAX_VALUE) { minFuel, pos ->
        val fuel = submarines.fold(0) { sum, sub ->
            sum + abs(sub - pos)
        }
//        println("Moving all to $pos\t cost $fuel\n")
        minFuel.coerceAtMost(fuel)
    }
    val answer = leastFuel
    println("Answer: $answer")
//    check(answer == 37)
    check(answer == 352331)
    return answer
}


private fun part2(submarines: List<Int>): Int {
    val maxPosition = submarines.maxOrNull()!!
    val leastFuel = (0..maxPosition).fold(Integer.MAX_VALUE) { minFuel, pos ->
        val fuel = submarines.foldIndexed(0) { index, sum, sub ->
            val diff = abs(sub - pos)
            sum + (0..diff).sum()
        }
        minFuel.coerceAtMost(fuel)
    }
    val answer = leastFuel
    println("Answer: $answer")
//    check(answer == 168)
    check(answer == 99266250)
    return answer
}

