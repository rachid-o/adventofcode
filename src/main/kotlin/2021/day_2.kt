package `2021`

import java.io.File

fun main() {
    val inputFilename = "day_2.txt"
    println("Reading file $resourcePath/$inputFilename")
    val commands = File("$resourcePath/$inputFilename")
        .readLines()
        .map {
            val (cmd, nr) = it.split(" ")
            cmd to nr.toInt()
        }

    println("Report size:  ${commands.size}")

    println("Answer 1: " + part1(commands))
    println("Answer 2: " + part2(commands))
}

private fun part1(commands: List<Pair<String, Int>>): Int {
    var position = 0
    var depth = 0
    commands.forEach { (cmd, increase) ->
        when (cmd) {
            "forward" -> position += increase
            "down" -> depth += increase
            "up" -> depth -= increase
        }
    }

    println("position: $position, depth: $depth")
    return position * depth
}

private fun part2(commands: List<Pair<String, Int>>): Int {
    var position = 0
    var depth = 0
    var aim = 0
    commands.forEach { (cmd, increase) ->
        when (cmd) {
            "forward" -> {
                position += increase
                depth += increase*aim
            }
            "down" -> aim += increase
            "up" -> aim -= increase
        }
    }

    println("position: $position, depth: $depth")
    return position * depth
}
