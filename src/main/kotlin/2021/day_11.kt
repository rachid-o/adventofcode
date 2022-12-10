package `2021`

import utils.Grid
import utils.toGridString
import java.io.File

fun main() {
    val inputFilename = "day_11.txt"
    println("Reading file $resourcePath/$inputFilename")
    val input = File(resourcePath, inputFilename)
        .readLines()
        .map { it.toCharArray().map { it.toString().toInt() } }
    println("input(${input.size}):\n${input.toGridString()}")
    println("Answer 1: " + part1(input))
    println("Answer 2: " + part2(input))
}

private fun part1(area: List<List<Int>>): Int {
    val grid = Grid(area)
    val answer = (1..100).fold(0) { sum, step ->
//        println("After step: $step\n${nextGrid}")
        sum + calcNextStep(grid)!!
    }

    println("Answer: $answer")
//    check(answer == 1656)
    check(answer == 1694)
    return answer
}

private fun part2(area: List<List<Int>>): Int {
    val grid = Grid(area)
    for (step in 1..Int.MAX_VALUE) {
        val flashesOfStep = calcNextStep(grid)
//        println("After step: $step\n${nextGrid}")
        if(flashesOfStep == null) {
            val answer = step
            println("Answer: $answer")
//            check(answer == 195)
            check(answer == 346)
            return answer
        }
    }
    return -1
}

private fun calcNextStep(grid: Grid<Int>): Int? {
    // Increase energy
    grid.getAll().forEach { (point, value) ->
        grid.update(point, value + 1)
    }
    // Flash
    val nrOfFlashes = flash(grid)
    if(nrOfFlashes == grid.getAll().size) {
        // For part 2 stop when all octopuses are flashing at the same time
        return null
    }
    // Reset energy
    grid.getAll()
        .filterValues { it > 9 }
        .keys.forEach { point->
            grid.update(point, 0)
    }
    return nrOfFlashes
}

private fun flash(grid: Grid<Int>): Int {
    var flashes = 0
    val pointsToFlash = grid.getAll().filterValues { it == 10 }.toMutableMap()
    while (pointsToFlash.isNotEmpty()) {
        val newPointsToFlash = mutableMapOf<Grid.Point, Int>()
        pointsToFlash.forEach { (point, value) ->
            grid.update(point, value + 1) // This is a flash
            flashes++
            val neighborsToInc = grid.getAllNeighbors(point).toMutableList()
            neighborsToInc.forEach { neighborPoint ->
                val newValue = grid.getValue(neighborPoint) + 1
                grid.update(neighborPoint, newValue)
                if (newValue == 10) {
                    newPointsToFlash[neighborPoint] = newValue
                }
            }
        }
        pointsToFlash.clear()
        pointsToFlash.putAll(newPointsToFlash)
    }
    return flashes
}
