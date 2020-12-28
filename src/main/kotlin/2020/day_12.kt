package `2020`

import com.ginsberg.cirkle.circular
import java.io.File
import java.lang.Math.abs

fun main() {
    val inputFilename = "day_12.txt"
    val input = parseFile("$resourcePath/$inputFilename")
    val answer = part1(input)
    println("Part 1 answer is: $answer")

    val answer2 = part2(input)
    println("Part 2 answer is: $answer2")
}

private fun part2(instructions: List<Instruction12>): Int {
    var pos = Pair(0, 0)
    var waypoint = Pair(10, 1)
    instructions.forEach { ins ->
        when(ins.action) {
            Direction.north -> waypoint = waypoint.nextPos(ins.action, ins.value)
            Direction.east -> waypoint = waypoint.nextPos(ins.action, ins.value)
            Direction.south -> waypoint = waypoint.nextPos(ins.action, ins.value)
            Direction.west -> waypoint = waypoint.nextPos(ins.action, ins.value)
            Direction.forward -> pos = Pair(pos.first + ins.value * waypoint.first, pos.second + ins.value * waypoint.second)
            Direction.right ->  {
                waypoint = when((ins.value / 90) % 4) {
                    1 -> Pair(waypoint.second, -1 * waypoint.first)
                    2 -> Pair(-1 * waypoint.first, -1 * waypoint.second)
                    3 -> Pair(-1 * waypoint.second, waypoint.first)
                    else -> waypoint
                }
            }
            Direction.left ->  {
                waypoint = when((ins.value / 90) % 4) {
                    1 -> Pair(-1 * waypoint.second, waypoint.first)
                    2 -> Pair(-1 * waypoint.first, -1 * waypoint.second)
                    3 -> Pair(waypoint.second, -1 * waypoint.first)
                    else -> waypoint
                }
            }
        }
    }
    println("\nEnd pos: $pos")
    return abs(pos.first) + abs(pos.second)
}


private fun part1(instructions: List<Instruction12>): Int {
    var pos = Pair(0, 0)
    var currDirection = Direction.east
    instructions.forEach { ins ->
        when(ins.action) {
            Direction.north -> pos = pos.nextPos(ins.action, ins.value)
            Direction.east -> pos = pos.nextPos(ins.action, ins.value)
            Direction.south -> pos = pos.nextPos(ins.action, ins.value)
            Direction.west -> pos = pos.nextPos(ins.action, ins.value)
            Direction.forward -> pos = pos.nextPos(currDirection, ins.value)
            Direction.left -> currDirection = turn(currDirection, -1 * ins.value)
            Direction.right -> currDirection = turn(currDirection, ins.value)
        }
    }
    println("\nEnd pos: $pos")
    return abs(pos.first) + abs(pos.second)
}

private val directions = listOf(Direction.north, Direction.east, Direction.south, Direction.west).circular()

private fun turn(direction: Direction, degrees: Int): Direction {
    val newIndex = directions.indexOf(direction) + (degrees/90)
    return directions[newIndex]
}

private fun Pair<Int, Int>.nextPos(direction: Direction, value: Int) = when(direction) {
    Direction.north -> copy(second = second + value)
    Direction.east -> copy(first = first + value)
    Direction.south -> copy(second = second - value)
    Direction.west -> copy(first = first - value)
    else -> this
}

private enum class Direction(val code: Char) {
    north('N'),
    east('E'),
    south('S'),
    west('W'),
    left('L'),
    right('R'),
    forward('F');

    companion object {
        fun fromChar(char: Char): Direction = values().firstOrNull { it.code == char }!!
    }
}

private data class Instruction12(val action: Direction, val value: Int) {
    override fun toString() = "${action}\t $value"
}

private fun parseFile(inputFile: String): List<Instruction12> {
    println("Reading file $inputFile")
    return File(inputFile).readLines()
        .map {
            val dir = Direction.fromChar(it.toCharArray().first())
            val value = it.substring(1, it.length).toInt()
            Instruction12(dir, value)
        }
}
