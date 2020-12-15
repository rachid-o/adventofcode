package `2020`

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

const val resourcePath = "src/main/resources/2020"

fun log(message: Any) {
    val timestamp = DateTimeFormatter.ofPattern("HH:mm:ss").format(ZonedDateTime.now())
    println("$timestamp  -  $message")
}
