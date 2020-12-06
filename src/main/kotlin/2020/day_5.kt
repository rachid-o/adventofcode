package `2020`

import java.io.File

// Plane has 128 rows 0..127 and 8 seats (colums) per row 0..7

fun main() {
    val inputFilename = "day_5.txt"
    val lines = File("$resourcePath/$inputFilename").readLines()

    println("nr of lines in file:  ${lines.size}")
//    part1(lines)
    part2(lines)
}

private fun part1(txtPasses: List<String>) {
    println("Part 1: ")
    val highestSeatID = txtPasses
        .map { parseBoardingPass(it) }
        .maxByOrNull { it.seatID }
    println("highestSeatID: $highestSeatID")
}

private fun part2(txtPasses: List<String>) {
    println("Part 2: ")
    val seats = txtPasses.map { parseBoardingPass(it) }

    val plane = mutableMapOf<Int, MutableSet<Int>>()
    seats.forEach { s ->
        plane.getOrPut(s.row, {mutableSetOf()}).add(s.col)
    }

    for(row in 1 until 127) {
        if(!plane.containsKey(row)) {
            continue
        }
        val cols = plane.getValue(row)
        if(cols.size < 7) {
            continue
        }
        if(cols.size != 8) {
            println("${row} ->  ${cols.size}  \t  ${cols}")
            val allSeats = setOf(0, 1, 2, 3, 4, 5, 6, 7)
            val missingSeats = allSeats - cols
            val missingSeatID = row * 8 + missingSeats.toList().first()
            println("My seat ID: $missingSeatID ")
        }
    }
}

data class Seat(val row: Int, val col : Int) {
    val seatID: Int
        get() = row * 8 + col

    override fun toString(): String {
        return "row $row, column $col, seat ID: $seatID"
    }
}

private fun parseBoardingPass(txtPass: String): Seat {
    var rowRange = 0..127
    for (i in 0 .. 6) {
        if(txtPass[i] == 'F') {
            rowRange = (rowRange.first)  until  rowRange.last - ((rowRange.last - rowRange.first) / 2)
        } else {
            rowRange = (rowRange.first + ((rowRange.last-rowRange.first)/2) + 1)  ..  rowRange.last
        }
    }
    var colRange = 0..7
    for (i in 7 .. 9) {
        if(txtPass[i] == 'L') {
            colRange = (colRange.first)  until  colRange.last - ((colRange.last - colRange.first) / 2)
        } else {
            colRange = (colRange.first + ((colRange.last-colRange.first)/2) + 1)  ..  colRange.last
        }
    }

    val row = rowRange.first
    val col = colRange.first

    return Seat(row, col)
}
