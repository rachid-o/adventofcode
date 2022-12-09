package `2022`

import utils.Grid
import java.io.File

fun main() {
    val inputFilename = "day_8.txt"
    println("Reading file $resourcePath/$inputFilename")
    val input = File(resourcePath, inputFilename)
        .readLines()
        .map { row -> row.toCharArray().map { col -> col.toString().toInt() } }

    val grid = Grid(input)
    println("input: \n$grid")
    println("${grid.getHeight()} x ${grid.getWidth()} \n")

    val answer1 = part1(grid)
    println("Answer 1: $answer1")

    val answer2 = part2(grid)
    println("Answer 2: $answer2")
}

private fun part1(grid: Grid): Int {
    return grid.getAll().keys.count { pos ->
        hasSmallerNeighbors(grid, pos)
    }
}

private fun part2(grid: Grid): Int {
    return grid.getAll().keys.map { pos ->
        scenicScore(grid, pos)
    }.maxOrNull()!!
}

fun scenicScore(grid: Grid, pos: Grid.Point): Int {
    val north = viewingDistance(grid, (pos.row downTo 0).map { pos.copy(row = it) })
    val south = viewingDistance(grid, (pos.row until  grid.getHeight()).map { pos.copy(row = it) })
    val east = viewingDistance(grid, (pos.col downTo 0).map { pos.copy(col = it) })
    val west = viewingDistance(grid, (pos.col until grid.getWidth()).map { pos.copy(col = it) })
//    println("$pos (${grid.getValue(pos)}) -> ${north * south * east * west}\t <=   $north * $south * $east * $west")
    return north * south * east * west
}

fun viewingDistance(grid: Grid, range: List<Grid.Point>): Int {
    val house = range.first()
    val height = grid.getValue(house)
    val trees = range.drop(1).map { grid.getValue(it) }
//    val viewableTrees = trees.takeWhile { it < height }
//    println("viewableTrees: $viewableTrees")
//    return viewableTrees.size
    var sum = 0
    for( t in trees) {
        sum += 1
        if(t >= height) {
            break;
        }
    }
    return sum
}

private fun hasSmallerNeighbors(grid: Grid, pos: Grid.Point): Boolean {
    val height = grid.getValue(pos)
    val neighbors = grid.getEdgeNeighborPoints(pos)
    if (neighbors.size < 4) {
        return true
    } else {
        // Find lower neighbors
        val smallerNeighbors = neighbors
            .map { it to grid.getValue(it) }
            .filter { (_, nHeight) -> nHeight < height }
            .map { (nPos, _) -> nPos }
        if(smallerNeighbors.isEmpty()) {
            return false
        } else {
            // NORTH
            if(canReachBorder(grid, (pos.row downTo 0).map { pos.copy(row = it) }))
                return true
            // SOUTH
            if(canReachBorder(grid, (pos.row until  grid.getHeight()).map { pos.copy(row = it) }))
                return true
            // EAST
            if(canReachBorder(grid, (pos.col downTo 0).map { pos.copy(col = it) }))
                return true
            // WEST
            if(canReachBorder(grid, (pos.col until grid.getWidth()).map { pos.copy(col = it) }))
                return true

            return false

        }
    }
}

private fun canReachBorder(
    grid: Grid,
    range: List<Grid.Point>,
): Boolean {
//    val house = range.first()
    var prevPos = range.first()
    for (newPos in range.drop(1)) {
        if (grid.getValue(newPos) < grid.getValue(prevPos)) {
//            prevPos = newPos
            continue
        } else {
            return false
        }
    }
    return true
}