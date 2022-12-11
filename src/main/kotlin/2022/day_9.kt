package `2022`

import utils.Grid
import java.io.File

const val E = '.'

fun main() {
    val inputFilename = "day_9.txt"
    println("Reading file $resourcePath/$inputFilename")
    val input = File(resourcePath, inputFilename)
        .readLines()
        .map {
            val (dir, steps) = it.split(" ")
            Direction.fromString(dir) to steps.toInt()
        }

    val grid = Grid.create(550, 500, E)
    println("input(${input.size}): \n$input")

    val answer1 = countVisitsByTail(grid, input, 2)
    println("Answer 1: $answer1")
    val answer2 = countVisitsByTail(grid, input, 10)
    println("Answer 2: $answer2")

    check(answer1 == 6406)
    check(answer2 == 2643)
}

private fun countVisitsByTail(grid: Grid<Char>, input: List<Pair<Direction, Int>>, ropeLength: Int): Int {
    val startPoint = Grid.Point(grid.getHeight()/2, grid.getWidth()/2)
    val rope = (0 until ropeLength).map { startPoint.copy() }.toMutableList()
    val visited = mutableListOf<Grid.Point>()

    input.forEach {(dir, steps) ->
        repeat(steps) {
            val head = rope[0]
            rope[0] = when(dir) {
                Direction.right -> {
                    head.copy(col = head.col + 1)
                }
                Direction.up -> {
                    head.copy(row = head.row - 1)
                }
                Direction.left -> {
                    head.copy(col = head.col - 1)
                }
                Direction.down -> {
                    head.copy(row = head.row + 1)
                }
                else -> throw IllegalStateException("$dir is unsupported direction for moving Head")
            }

            rope.drop(1).forEachIndexed { index, nextKnot ->
                val prevKnot = rope[index]
                if(!grid.getAllNeighbors(nextKnot).contains(prevKnot)) {
                    rope[index+1] = when(getRelativeDir(prevKnot, nextKnot)) {
                        Direction.left -> nextKnot.copy(col = nextKnot.col - 1)
                        Direction.right -> nextKnot.copy(col = nextKnot.col + 1)
                        Direction.up -> nextKnot.copy(row = nextKnot.row - 1)
                        Direction.down -> nextKnot.copy(row = nextKnot.row + 1)
                        Direction.middle -> nextKnot // Do nothing
                        Direction.upright -> nextKnot.copy(row = nextKnot.row - 1, col = nextKnot.col + 1)
                        Direction.upleft -> nextKnot.copy(row = nextKnot.row - 1, col = nextKnot.col - 1)
                        Direction.downright -> nextKnot.copy(row = nextKnot.row + 1, col = nextKnot.col + 1)
                        Direction.downleft -> nextKnot.copy(row = nextKnot.row + 1, col = nextKnot.col - 1)
                    }
                }
            }
            visited.add(rope.last())
        }
    }
//    grid.print(rope)
//    grid.print(visited)
    return visited.toSet().size
}


private fun getRelativeDir(posH: Grid.Point, posT: Grid.Point): Direction {
    val relDir = if (posH.row < posT.row) {
        Direction.up
    } else if (posH.row > posT.row) {
        Direction.down
    } else {
        Direction.middle
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


private fun Grid<Char>.print(rope: List<Grid.Point>) {
    val grid = Grid.fromGrid(this, E)
    var knot = rope.size
    rope
        .reversed()
        .forEach { p ->
            knot--
            grid.update(p, Character.forDigit(knot, 10))
        }
    println(grid)
    println("rope: $rope")
    println()
}
