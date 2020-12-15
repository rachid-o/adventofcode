package `2020`

import java.time.Duration
import kotlin.system.measureTimeMillis

fun main() {
    val input = listOf(0, 13, 16, 17, 1, 10, 6)
    println("input:  ${input} \n")

    val durationms = measureTimeMillis {
        day15(input, 2020)
        day15(input, 30000000)
    }
    val duration = Duration.ofMillis(durationms)
    println("\nDuration: ${duration.seconds} secs \t ($durationms ms) " )
}

private fun day15(input: List<Int>, spokenNumber: Int) {
    var lastSpokenNr = -1
    val spokenBefore = mutableMapOf<Int, Pair<Int?, Int?>>()
    var globPrevPair: Pair<Int?, Int?> = Pair(null, null)

    (0 until spokenNumber).forEach { index ->
        val turn = index + 1
        if(index < input.size) {
            val nr = input[index]
            lastSpokenNr = nr
            globPrevPair = Pair(turn, null)
        } else {

            val prevIndex = spokenBefore.getOrDefault(lastSpokenNr, Pair(null, null))
            lastSpokenNr = if(prevIndex.first == null || prevIndex.second == null) {
                if(prevIndex.first == null) {
                    globPrevPair = Pair(turn, null)
                } else if(prevIndex.second == null) {
                    val first = spokenBefore.get(0)?.first
                    globPrevPair = Pair(turn, first)
                }
                0
            } else {
                val newLastSpokenNr = prevIndex.first!! - prevIndex.second!!
                val first = spokenBefore.get(newLastSpokenNr)?.first
                globPrevPair = Pair(turn, first)
                newLastSpokenNr
            }
        }
        spokenBefore.put(lastSpokenNr, globPrevPair)
        if(index % (spokenNumber / 10) == 0 ) {
            log("$index")
        }
    }
    println("\n$spokenNumber'th spoken number for $input is: $lastSpokenNr \n")
}
