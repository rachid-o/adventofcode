package `2021`

import utils.Grid
import utils.Grid.Point
import utils.log
import utils.printDuration
import utils.toGridString
import java.io.File

fun main() {
    val inputFilename = "day_15.txt"
    println("Reading file $resourcePath/$inputFilename")
    val input = File(resourcePath, inputFilename)
        .readLines()
        .map { it.toCharArray().map { c -> c.toString().toInt() } }
    println("input(${input.size}):\n${input.toGridString()}")
//    println("Answer 1: " + printDuration { part1(input) } )
    println("Answer 2: " + printDuration { part2(input) } )
}

private fun part1(area: List<List<Int>>): Int {
    val answer = findLowestRisk(area)
    println("Answer: $answer")
//    check(answer == 40)
    check(answer == 472)
    return answer
}

private fun part2(area: List<List<Int>>): Int {
    val actualCave = area.expand()
//    println("actualCave: \n $actualCave")

    val answer = findLowestRisk(actualCave)
    println("Answer: $answer")

    check(answer == 315)
//    check(answer == 2851)
//    Answer: 2851
//    08:32:20  -  Duration: 28453 secs 	 (28453991 ms)	  PT7H54M13.991S
    // Took the whole night because my laptop battery died half way :'D
    return answer
}

private typealias Cave = List<List<Int>>

const val expand = 5
private fun Cave.expand(): Cave {
//    var newList = mutableListOf<Int>()
    val newCave2 = mutableListOf<List<Int>>()
    val horizontalExpanded = this.map { row ->
        val newRow = mutableListOf<Int>()
        repeat(expand) { step ->
            newRow.addAll(row.map { wrap( it + step) })
        }
        newRow
    }

    newCave2.addAll(horizontalExpanded)
    (1 until expand).forEach { step ->
        horizontalExpanded.forEach { row ->
            val mr = row.map { wrap(it + step) }
//            println(" $step row: $row  ->  $mr")
            newCave2.add(mr)
        }
    }
    return newCave2
}

private fun wrap(value: Int) =
    if(value % 9 != 0) {
        value % 9
    } else {
        9
    }


private fun findLowestRisk(area: List<List<Int>>): Int {
    val grid = Grid(area)
    val startPoint = Point(0, 0)
    val end = Point(area.size - 1, area.first().size - 1)
    val totalRisks = mutableMapOf<Point, Int>()
    val processedPoints = mutableSetOf<Point>()
    totalRisks[startPoint] = 0

    val total = (grid.getAll().size)
    repeat(grid.getAll().size - 1) {
        if(it % 1000 == 0) {
            log("$it / $total")
        }
        val unprocessedPoints = totalRisks.filterKeys { !processedPoints.contains(it) }
        val cheapestNext = minDist(unprocessedPoints)
        processedPoints.add(cheapestNext)

        grid.getEdgeNeighborPoints(cheapestNext)
            .filter { p -> !processedPoints.contains(p) }   // Not processed
            .forEach { point ->
                if (totalRisks.containsKey(cheapestNext)) {
                    val nextRisk = totalRisks[cheapestNext]!! + grid.getValue(point)
                    if (nextRisk < totalRisks.getOrDefault(point, Int.MAX_VALUE)) {
                        totalRisks[point] = nextRisk
                    }
                }
            }
    }
    return totalRisks.getValue(end)
}

fun minDist(totalRisks: Map<Point, Int>): Point {
    var min = Int.MAX_VALUE
    var minPoint = Point(-1, -1)

    totalRisks.forEach { (point, risk) ->
        if(risk <= min) {
            min = risk
            minPoint = point
        }
    }
    return minPoint
}
