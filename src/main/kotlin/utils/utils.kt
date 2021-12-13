package utils

import java.math.BigInteger
import java.time.Duration
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


fun log(message: Any) {
    val timestamp = DateTimeFormatter.ofPattern("HH:mm:ss").format(ZonedDateTime.now())
    println("$timestamp  -  $message")
}
val NEWLINE = System.lineSeparator()

/**
 * Executes the given [block] and prints the duration
 */
fun <T> printDuration(block: () -> T): T {
    val start = System.currentTimeMillis()
    val result = block()
    val durationms = System.currentTimeMillis() - start
    val duration = Duration.ofMillis(durationms)
    log("Duration: ${duration.seconds} secs \t ($durationms ms)\t  $duration\n")
    return result
}

fun Int.toBinary() = Integer.toBinaryString(this)
fun String.toDecimal() = BigInteger(this, 2).toLong()
fun String.sorted() = this.toCharArray().sorted().joinToString("")
fun List<List<Number>>.toGridString() = joinToString("\n") { it.joinToString("") }

fun Collection<Int>.multiply() = this.reduce { acc, i -> i * acc }
fun Collection<Long>.multiply() = this.reduce { acc, i -> i * acc }

fun <K, V> Map<K, V>.getKey(value: V) =
    entries.firstOrNull { it.value == value }?.key


// edge adjacent neighbors
fun List<List<Int>>.edgeNeighbors(rowIndex: Int, colIndex: Int) =
    edgeNeighborPositions(rowIndex, colIndex)
        .map { this[it.first][it.second] }

fun List<List<Int>>.edgeNeighborPositions(row: Int, col: Int) = listOf(
    Pair(row - 1, col),
    Pair(row, col - 1),
    Pair(row, col + 1),
    Pair(row + 1, col),
)
    .filter { it.first in this.indices && it.second in this[0].indices }

fun List<List<Int>>.cornerNeighborPositions(row: Int, colIndex: Int) = listOf(
    Pair(row - 1, colIndex - 1),
    Pair(row - 1, colIndex + 1),
    Pair(row + 1, colIndex - 1),
    Pair(row + 1, colIndex + 1),
)
    .filter { it.first in this.indices && it.second in this[0].indices }



