package `2021`

import utils.sorted
import java.io.File

fun main() {
    val inputFilename = "day_8.txt"
    println("Reading file $resourcePath/$inputFilename")
    val input = File(resourcePath, inputFilename)
        .readLines()
        .map { line ->
            val (strSignals, strOutput) = line.split("|").map { it.trim() }
            strSignals.split(" ").map(String::sorted) to strOutput.split(" ").map(String::sorted)
        }
    println("input(${input.size}): $input")
    println("Answer 1: " + part1(input))
    println("Answer 2: " + part2(input))
}

private fun part1(input: List<Pair<List<String>, List<String>>>): Int {
    val outputs = input
        .map { it.second }
    val sizesToCount = KNOWN_SEGMENTS.values.toSet()
    val answer = outputs.fold(0) { sum, output ->
        sum + output.count { sizesToCount.contains(it.length) }
    }
    println("Answer: $answer")
//    check(answer == 26)
    check(answer == 255)
    return answer
}

private fun part2(input: List<Pair<List<String>, List<String>>>): Int {
    val answer = input
        .fold(0) { sum, strSignalsAndOutput->
            sum + decodeDigits(strSignalsAndOutput)
    }
    println("Answer: $answer")
//    check(answer == 61229)
    check(answer == 982158)
    return answer
}

val KNOWN_SEGMENTS = mapOf(
//    0 to 6,
    1 to 2,
//    2 to 5,
//    3 to 5,
    4 to 4,
//    5 to 5,
//    6 to 6,
    7 to 3,
    8 to 7,
//    9 to 6,
)

private fun decodeDigits(signalsAndOutput: Pair<List<String>, List<String>>): Int {
    val (signals, output) = signalsAndOutput
    val uniqueSegments = signals.mapNotNull { signal ->
        KNOWN_SEGMENTS.getKey(signal.length)?.let {
            it to signal
        }
    }.toMap()
    val signal1 = uniqueSegments.getValue(1)
    val signal4 = uniqueSegments.getValue(4)
    val signal7 = uniqueSegments.getValue(7)
    val allSegments =// it is either 2 or 5
        signals.associate { signal ->
            if (uniqueSegments.values.contains(signal)) {
                signal to uniqueSegments.getKey(signal)!!
            } else {
                if (signal.length == 6) {
                    // it is either 0, 6 or 9
                    if (signal4.toCharArray().all { signal.toCharArray().contains(it) }) {
                        signal to 9
                    } else if (signal1.toCharArray().all { signal.toCharArray().contains(it) }
                        &&
                        signal7.toCharArray().all { signal.toCharArray().contains(it) }) {
                        signal to 0
                    } else {
                        signal to 6
                    }
                } else {
                    if (signal1.toCharArray().all { signal.toCharArray().contains(it) }) {
                        signal to 3
                    } else {
                        // it is either 2 or 5
                        val commonSegmentsWith4 = signal.toCharArray().count { signal4.toCharArray().contains(it) }
                        if(commonSegmentsWith4 > 2) {
                            signal to 5
                        } else {
                            signal to 2
                        }
                    }
                }
            }
        }.mapKeys {
            it.key.sorted()
        }

    return output
        .map { allSegments.getValue(it) }
        .joinToString("")
        .toInt()
}


private fun <K, V> Map<K, V>.getKey(value: V) =
    entries.firstOrNull { it.value == value }?.key
