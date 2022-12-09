package `2022`

import utils.Grid
import java.io.File
import java.lang.IllegalStateException

const val E = 0
const val H = 1
const val T = 2

fun main() {
    val inputFilename = "day_9.txt"
    println("Reading file $resourcePath/$inputFilename")
    val input = File(resourcePath, inputFilename)
        .readLines()
        .map {
            val (dir, steps) = it.split(" ")
            Direction.fromString(dir) to steps.toInt()
        }

//    grid 5 rows, 6 cols
//    6 x 5
    val grid = Grid.create(600, 600)
    println("input(${input.size}): \n$input")


    val answer1 = part1(grid, input)
    println("Answer 1: $answer1")

//    val answer2 = part2(grid)
//    println("Answer 2: $answer2")
}

private fun part1(grid: Grid, input: List<Pair<Direction, Int>>): Int {
    var posH = Grid.Point(grid.getHeight()/2, grid.getWidth()/2)
    var posT = posH
    grid.update(posH, H)
    println("initial state: \n$grid")

    val visited = mutableListOf<Grid.Point>()
    visited.add(posT)

    input.forEachIndexed { lineNr, (dir, steps) ->
//        println("L $lineNr: $steps to $dir")
        repeat(steps) {
            val newPosH = when(dir) {
                Direction.right -> {
                    posH.copy(col = posH.col + 1)
                }
                Direction.up -> {
                    posH.copy(row = posH.row - 1)
                }
                Direction.left -> {
                    posH.copy(col = posH.col - 1)
                }
                Direction.down -> {
                    posH.copy(row = posH.row + 1)
                }
                else -> throw IllegalStateException("$dir is unsupported direction for moving Head")
            }
            grid.update(posH, E)
            grid.update(newPosH, H)
//          println("$grid\n")
            posH = newPosH

            if(!grid.getAllNeighbors(posT).contains(posH)) {
                val relDir = getRelativeDir(posH, posT)
//                println("H($posH) is now from T($posT): $relDir")
                val newPosT = when(relDir) {
                    Direction.left -> posT.copy(col = posT.col - 1)
                    Direction.right -> posT.copy(col = posT.col + 1)
                    Direction.up -> posT.copy(row = posT.row - 1)
                    Direction.down -> posT.copy(row = posT.row + 1)
                    Direction.middle -> posT // Do nothing
                    Direction.upright -> posT.copy(row = posT.row - 1, col = posT.col + 1)
                    Direction.upleft -> posT.copy(row = posT.row - 1, col = posT.col - 1)
                    Direction.downright -> posT.copy(row = posT.row + 1, col = posT.col + 1)
                    Direction.downleft -> posT.copy(row = posT.row + 1, col = posT.col - 1)
                }
                visited.add(newPosT)
                grid.update(posT, E)
                grid.update(newPosT, T)
                posT = newPosT
            }
        }
    }

    return visited.toSet().size
}


private fun getRelativeDir(posH: Grid.Point, posT: Grid.Point): Direction {
    val relDir: Direction
    if (posH.row < posT.row) {
        relDir = Direction.up
    } else if (posH.row > posT.row) {
        relDir = Direction.down
    } else {
        relDir = Direction.middle
    }

    if (posH.col > posT.col) {
        // left
        return when(relDir) {
            Direction.middle -> Direction.right
            Direction.up -> Direction.upright
            Direction.down -> Direction.downright
            else -> throw IllegalStateException("not possible")
        }
    } else if (posH.col < posT.col) {
        // Right
        return when(relDir) {
            Direction.middle -> Direction.left
            Direction.up -> Direction.upleft
            Direction.down -> Direction.downleft
            else -> throw IllegalStateException("not possible")
        }
    } else {
        return relDir
    }
}

private enum class Direction(val code: String) {
    left("L"),
    right("R"),
    up("U"),
    down("D"),
    middle("M"),
    upleft("UL"),
    upright("UR"),
    downleft("DL"),
    downright("DR");

    companion object {
        fun fromString(code: String): Direction = values().firstOrNull { it.code == code }!!
    }
}
