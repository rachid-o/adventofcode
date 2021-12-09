package `2021`

import utils.edgeNeighborPositions
import utils.multiply
import utils.edgeNeighbors
import java.io.File

fun main() {
    val inputFilename = "day_9.txt"
    println("Reading file $resourcePath/$inputFilename")
    val input = File(resourcePath, inputFilename)
        .readLines()
        .map { it.toCharArray().map { it.toString().toInt() } }
    println("input(${input.size}): $input")
    println("Answer 1: " + part1(input))
    println("Answer 2: " + part2(input))
}

private fun part1(area: List<List<Int>>): Int {
    val lowPoints = area.flatMapIndexed { y, row ->
        row.mapIndexedNotNull { x, point ->
            val neighbors = area.edgeNeighbors(y, x)
            if (neighbors.all { it > point }) {
                point
            } else {
                null
            }
        }
    }
    println("lowPoints: ${lowPoints.sum()} - $lowPoints")
    val answer = lowPoints.sum() + lowPoints.size
    println("Answer: $answer")
    check(answer == 496)
    return answer
}


private fun part2(area: List<List<Int>>): Int {
    val basinSizes = area.flatMapIndexed { y, row ->
        row.mapIndexedNotNull { x, point ->
            val neighbors = area.edgeNeighbors(y, x)
            if (neighbors.all { it > point }) {
                // This is a low point, find the basin
                findBasinSize(area, Pair(y, x))
            } else {
                null
            }
        }
    }
    val largestBasins = basinSizes.sorted().subList(basinSizes.size - 3, basinSizes.size)
    val answer = largestBasins.multiply()
    println("Answer: $answer")
    check(answer == 902880)
    return answer
}

fun findBasinSize(area: List<List<Int>>, point: Pair<Int, Int>): Int {
    val basinPoints = mutableSetOf(point)
    var newNeighbors = setOf(point)
    do {
        val basinNeighbors = newNeighbors.flatMap { getBasinNeighbors(area, it) }.toSet()
        newNeighbors = basinNeighbors.toSet()
        basinPoints.addAll(newNeighbors)
    } while (newNeighbors.isNotEmpty())

    return basinPoints.size
}

private fun getBasinNeighbors(
    area: List<List<Int>>,
    point: Pair<Int, Int>
): List<Pair<Int, Int>> {
    val allNeighbors = area.edgeNeighborPositions(point.first, point.second)
    val basinNeighbors = allNeighbors.filter { (y, x) ->
        area[y][x] != 9 && area[y][x] > area[point.first][point.second]
    }
    return basinNeighbors
}
