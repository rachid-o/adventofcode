package `2020`

import java.io.File


fun main() {
    val inputFilename = "day_9.txt"
    val data = parseFile("$resourcePath/$inputFilename")
    println("nr of numbers in file:  ${data.size}")

    println("Part 1: " + findInvalidNumber(data))

    part2(data)
}

const val preamble = 25

private fun findInvalidNumber(data: List<Long>): Long? {
    for(position in preamble until data.size) {
        val prevNrs = data.subList((position-preamble), position)
        val nr = data.get(position)
        if(!isSumOfAnyPrevs(nr, prevNrs)) {
            return nr
        }
    }
    return null
}

private fun part2(data: List<Long>) {
    println("Part 2: ")
    val invalidNr = findInvalidNumber(data)!!
    val summands = findSummands(invalidNr, data)
    println("summands: $summands \t sum: ${summands.sum()}")
    val min = summands.minOrNull()!!
    val max = summands.maxOrNull()!!
    println("Answer: $min + $max = ${min + max}")
}

fun findSummands(
    nr: Long,
    prevNrs: List<Long>,
): List<Long> {

    for(i in prevNrs.indices) {
        (1 until prevNrs.size)
            .filterNot { i > it }
            .forEach { j ->
                val potentialSummands = prevNrs.subList(i, j)
                if(potentialSummands.sum() == nr) {
                    return potentialSummands
            }
        }
    }

    return emptyList()
}


fun isSumOfAnyPrevs(nr: Long, prevNrs: List<Long>): Boolean {
    prevNrs.forEach { first ->
        prevNrs.forEach { second ->
            if (first + second == nr) {
                return true
            }
        }
    }
    return false
}

private fun parseFile(inputFile: String) = File(inputFile)
        .readLines()
        .map { it.toLong() }
