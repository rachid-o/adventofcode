package `2020`

import java.io.File


fun main() {
    val inputFilename = "day_11.txt"
    val layout = parseFile("$resourcePath/$inputFilename")
    println("input:")
    printLayout(layout)

    val occupiedSeats1 = findEquilibrium(layout, ::applyRules1)
    println("Occupied seats part 1: $occupiedSeats1 ")

    val occupiedSeats2 = findEquilibrium(layout, ::applyRules2)
    println("Occupied seats part 2: $occupiedSeats2 ")
}

private fun findEquilibrium(layout: Array<Array<Pos>>, rules: (Array<Array<Pos>>) -> Array<Array<Pos>> ): Int {
    var currentLayout = layout
    while(true) {
        val nextLayout = rules(currentLayout)
        if(nextLayout contentDeepEquals currentLayout) {
            return nextLayout.sumOf { it.count { it == Pos.occupied } }
        } else {
            currentLayout = nextLayout
        }
    }
}


private fun applyRules2(layout: Array<Array<Pos>>): Array<Array<Pos>> {
    return layout.mapIndexed { rowIndex, row ->
        row.mapIndexed { colIndex, seat: Pos ->
            val visibleSeats = layout.visibleSeats(rowIndex, colIndex)
            val occupiedSeats = visibleSeats.count { it == Pos.occupied }
            if(seat == Pos.empty && occupiedSeats == 0) {
                Pos.occupied
            } else if(seat == Pos.occupied && occupiedSeats >= 5) {
                Pos.empty
            } else {
                seat
            }
        }.toTypedArray()
    }.toTypedArray()
}

private fun Array<Array<Pos>>.inGrid(pos: Pair<Int, Int>) =
    pos.first in this.indices && pos.second in this[0].indices

private fun Array<Array<Pos>>.visibleSeats(rowIndex: Int, colIndex: Int): List<Pos> {
    // rowDirection, colDirection
    val directions = listOf(
        Pair(-1, -1),   // up left
        Pair(-1, 0),    // up
        Pair(-1, +1),   // up right
        Pair(0, +1),    // right
        Pair(+1, +1),   // down right
        Pair(+1, 0),    // down
        Pair(+1, -1),   // down left
        Pair(0, -1),    // left
    )

    val visSeats = mutableListOf<Pos>()
    directions.forEach { dir ->
        var newPos = Pair(rowIndex + dir.first, colIndex + dir.second)
        while(inGrid(newPos)) {
            val seat: Pos = this[newPos.first][newPos.second]
            if(seat != Pos.floor) {
                visSeats.add(seat)
                break
            }
            newPos = Pair(newPos.first + dir.first, newPos.second + dir.second)
        }
    }
    return visSeats
}


private fun applyRules1(layout: Array<Array<Pos>>): Array<Array<Pos>> {
    return layout.mapIndexed { rowIndex, row ->
        row.mapIndexed { colIndex, seat: Pos ->
            val neighbors = layout.neighbors(rowIndex, colIndex)
            val occupiedNeighbors = neighbors.count { it == Pos.occupied }
            if(seat == Pos.empty && occupiedNeighbors == 0) {
                Pos.occupied
            } else if(seat == Pos.occupied && occupiedNeighbors >= 4) {
                Pos.empty
            } else {
                seat
            }
        }.toTypedArray()
    }.toTypedArray()
}

private fun Array<Array<Pos>>.neighbors(rowIndex: Int, colIndex: Int) = listOf(
        Pair(rowIndex - 1, colIndex - 1),
        Pair(rowIndex - 1, colIndex),
        Pair(rowIndex - 1, colIndex + 1),
        Pair(rowIndex, colIndex - 1),
        Pair(rowIndex, colIndex + 1),
        Pair(rowIndex + 1, colIndex - 1),
        Pair(rowIndex + 1, colIndex),
        Pair(rowIndex + 1, colIndex + 1),
    )
        .filter { it.first in this.indices && it.second in this[0].indices }
        .map { this[it.first][it.second] }


private enum class Pos(val code: Char) {
    floor('.'),
    empty('L'),
    occupied('#');
    override fun toString() = code.toString()
}

private fun fromChar(char: Char): Pos = Pos.values().firstOrNull { it.code == char }!!

private fun parseFile(inputFile: String): Array<Array<Pos>> {
//    println("Pos values: " + Pos.values().map { it.name })
    return File(inputFile)
        .readLines()
        .map { line ->
            line.toCharArray()
                .map{ fromChar(it) }.toTypedArray()
        }.toTypedArray()
}

private fun printLayout(layout: Array<Array<Pos>>) = layout.forEach { println(it.joinToString("")) }
