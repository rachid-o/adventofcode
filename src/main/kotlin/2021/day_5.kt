package `2021`

import java.io.File

fun main() {
    val inputFilename = "day_5.txt"
    println("Reading file $resourcePath/$inputFilename")
    val lines = parseFile(File(resourcePath, inputFilename))
//    println("input:")
//    lines.forEach { println(it) }

    println("Answer 1: " + part1(lines))
    println("Answer 2: " + part2(lines))
}

private fun part1(lines: List<Line>): Int {
    val allPoints = lines.filter { line ->
        line.from.first == line.to.first || line.from.second == line.to.second
    }.flatMap { calculatePoints(it) }
    val diagram = Diagram.create(allPoints.highestPoint())
    allPoints.forEach {
        diagram.mark(it)
    }
    val answer = diagram.countLargerThan(1)
//    check(answer == 5)
    check(answer == 7414)
    return answer
}

private fun part2(lines: List<Line>): Int {
    val allPoints = lines.flatMap { calculatePoints(it) }
    val diagram = Diagram.create(allPoints.highestPoint())
    allPoints.forEach {
        diagram.mark(it)
    }
    val answer = diagram.countLargerThan(1)
    check(answer == 19676)
    return answer
}

private fun List<Pair<Int, Int>>.highestPoint(): Pair<Int, Int> = reduce { acc, pair ->
    Pair(
        if (acc.first > pair.first) acc.first else pair.first,
        if (acc.second > pair.second) acc.second else pair.second
    )
}

private fun calculatePoints(line: Line) =
    if (line.from.first == line.to.first) {
        val range = if (line.from.second < line.to.second) {
            line.from.second..line.to.second
        } else {
            line.to.second..line.from.second
        }
        range.map { Pair(line.from.first, it) }
    } else if (line.from.second == line.to.second) {
        val range = if (line.from.first < line.to.first) {
            line.from.first..line.to.first
        } else {
            line.to.first..line.from.first
        }
        range.map { Pair(it, line.from.second) }
    } else {
        val xRange = if (line.from.first < line.to.first) {
            line.from.first..line.to.first
        } else {
            line.from.first downTo line.to.first
        }
        val yRange = if (line.from.second < line.to.second) {
            line.from.second..line.to.second
        } else {
            line.from.second downTo line.to.second
        }
        xRange.zip(yRange) { x, y -> Pair(x, y) }
    }


/**
 * Pair of x,y
 */
private data class Line(val from: Pair<Int, Int>, val to: Pair<Int, Int>)

private data class Diagram(val rows: List<MutableList<Int>>) {
    override fun toString() = rows.joinToString("\n")
    fun mark(point: Pair<Int, Int>) {
        rows[point.second][point.first]++
    }

    fun countLargerThan(largerThan: Int) = rows.sumOf { it.count { it  > largerThan } }

    companion object {
        fun create(size: Pair<Int, Int>): Diagram {
            println("Creating diagram of $size")
            return Diagram(List(size.second+1) {
                MutableList(size.first+1) { 0 }
            })
        }
    }
}

private fun parseFile(inputFile: File): List<Line> {
    val lines = inputFile.readLines()
        .map { strLine ->
            val (from, to) = strLine.split(" -> ")
                .map {
                    val (x, y) = it.split(",").map(String::toInt)
                    Pair(x, y)
                }
            Line(from, to)
        }

    return lines
}
