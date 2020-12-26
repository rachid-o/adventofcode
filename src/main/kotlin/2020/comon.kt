package `2020`

import java.time.Duration
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

const val resourcePath = "src/main/resources/2020"

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
