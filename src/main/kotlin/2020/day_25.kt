package `2020`

import utils.log
import utils.printDuration
import java.io.File
import java.time.Duration
import kotlin.system.measureTimeMillis

const val DIVIDER = 20201227

fun main() {
    val inputFilename = "day_25.txt"
    val input = parseFile("$resourcePath/$inputFilename")
    val key1 = input[0]
    val key2 = input[1]
    println("Key 1:  $key1")
    println("Key 2:  $key2 \n")

    part1(key1, key2)
}

fun part1(pubKey1: Long, pubKey2: Long) {
    println("Part 1: ")

    val durationms = measureTimeMillis {
        val answer1 = calcEnryptionKey(pubKey1, pubKey2)
        println("Part 1 answer is: $answer1")
    }
    val duration = Duration.ofMillis(durationms)
    println("\nDuration: ${duration.seconds} secs \t ($durationms ms) " )}

// AKA transform
fun calcPublicKey(subjectNr: Long, loopSize: Long): Long {
    var currVal: Long = 1
    (1..loopSize).forEach { step ->
        currVal *= subjectNr
        currVal %= DIVIDER
    }
    return currVal
}

fun determineLoopSize(subjectNr: Long, publicKey: Long): Long {
    var loopSize: Long = 1
    var pKey: Long = 1
    while(true) {
        pKey = pKey * subjectNr % DIVIDER;
        if(pKey == publicKey) {
            return loopSize
        }

        if(loopSize % 100_000 == 0L) {
            log("${loopSize / 100_000}")
        }
        loopSize++
    }
}


fun calcEnryptionKey(pubKey1: Long, pubKey2: Long): Long {
    return printDuration {
        val loopSize1 = determineLoopSize(7, pubKey1)
        println("$pubKey1 has loopSize: $loopSize1")
        calcPublicKey(pubKey2, loopSize1)
    }
}


private fun parseFile(inputFile: String) =
    File(inputFile).readLines()
        .map{ it.toLong() }
