package utils

import java.math.BigInteger
import java.time.Duration
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


fun log(message: Any) {
    val timestamp = DateTimeFormatter.ofPattern("HH:mm:ss").format(ZonedDateTime.now())
    println("$timestamp  -  $message")
}


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

fun Collection<Int>.multiply() = this.reduce { acc, i -> i * acc }
fun Collection<Long>.multiply() = this.reduce { acc, i -> i * acc }

