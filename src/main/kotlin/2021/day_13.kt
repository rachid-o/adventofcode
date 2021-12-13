package `2021`

import utils.NEWLINE
import java.io.File
import java.lang.IllegalArgumentException

fun main() {
    val inputFilename = "day_13.txt"
    println("Reading file $resourcePath/$inputFilename")
    val (paper, foldInstructions) = parseFile(inputFilename)
//    println("input dots: $dots")
    println("Fold instructions: $foldInstructions")
    println("Begin with dots: ${paper.countDots()}")
//    println("Paper: \n$paper")
    println("Answer 1: " + part1(paper, foldInstructions))
}

private fun part1(paper: Paper, foldInstructions: List<FoldInstruction>): Int {
    var prevPaper = paper
//    foldInstructions.forEach { instruction ->
    val instruction = foldInstructions.first()
//        println("instruction: $instruction")
        val (axis, position) = instruction
        prevPaper = when(axis) {
            Axis.X -> {
                val newPaper = Paper(position, prevPaper.height)
                (0 until newPaper.height).forEach { y ->
//                    println("Y = $y")
                    (0 until newPaper.width).forEach { x ->
                        val oldPaperValue = prevPaper.getValue(x, y)
                        if(oldPaperValue) {
                            newPaper.update(x, y, oldPaperValue)
                        }
                        val xOld = prevPaper.width-x
                        val oldPaperValue2 = prevPaper.getValue(xOld, y)
                        if(oldPaperValue2) {
                            newPaper.update(x, y, oldPaperValue2)
                        }
                    }
                }
                newPaper
            }
            Axis.Y -> {
                val newPaper = Paper(prevPaper.width, position)
                (0 until newPaper.width).forEach { x ->
//                    println("X = $x")
                    (0 until newPaper.height).forEach { y ->
                        val oldPaperValue = prevPaper.getValue(x, y)
                        if(oldPaperValue) {
                            newPaper.update(x, y, oldPaperValue)
                        }
                        val yOld = prevPaper.height-y
                        val oldPaperValue2 = prevPaper.getValue(x, yOld)
                        if(oldPaperValue) {
                            newPaper.update(x, y, oldPaperValue2)
                        }
                    }
                }
                newPaper
            }
        }
//        println("New paper: \n$prevPaper")
//    }
    println("Paper result: \n$prevPaper")

    val answer = prevPaper.countDots()
    println("Answer: $answer")
//    check(answer == 17)
    check(answer == 687)
//    check(answer < 835)
    return answer
}

private typealias FoldInstruction = Pair<Axis, Int>

private enum class Axis {
    X,
    Y
}

private fun parseFile(inputFilename: String): Pair<Paper, List<FoldInstruction>> {
    val (strDots, strFolds) = File(resourcePath, inputFilename)
        .readText()
        .split("$NEWLINE$NEWLINE")

    val dots = strDots.split(NEWLINE)
        .map {
            val (x, y) = it.split(",").map(String::toInt)
            Paper.Point(x, y)
        }

    val width = dots.maxOf { it.x } + 1
    val height = dots.maxOf { it.y } + 1
    val paper = Paper(width, height)
    dots.forEach { p ->
        paper.update(p, true)
    }

    val folds = strFolds.split(NEWLINE)
        .map {
            val (strAxis, strPosition) = it
                .substring("fold along ".length)
                .split("=")
            val axis = when(strAxis) {
                "x" -> Axis.X
                "y" -> Axis.Y
                else -> throw IllegalArgumentException("Incorrect fold instruction: $it")
            }
            Pair(axis, strPosition.toInt())
        }

    return Pair(paper, folds)
}


private class Paper(val width: Int, val height: Int) {

    private val area: List<MutableList<Boolean>> = (0..width).map { _ ->
        (0..height).map { _ ->
            false
        }.toMutableList()
    }

    fun update(p: Point, newValue: Boolean) {
        area[p.x][p.y] = newValue
    }

    fun update(x: Int, y: Int, newValue: Boolean) {
        area[x][y] = newValue
    }

    fun getValue(p: Point) = area[p.x][p.y]
    fun getValue(x: Int, y: Int) = area[x][y]

    fun countDots() = area.flatten().count { it }


    override fun toString(): String {
        return (0 until height).joinToString(NEWLINE) { y ->
            (0 until width).joinToString("") { x ->
                getValue(Point(x, y)).toStr()
            }
        } + "\n$width x $height\n"
    }

    data class Point(val x: Int, val y: Int) {
        override fun toString() = "$x,$y"
    }
}

private fun Boolean.toStr() = if (this) {
    "#"
} else {
    "."
}

