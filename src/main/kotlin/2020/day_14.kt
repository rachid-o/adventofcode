package `2020`

import java.io.File
import java.lang.Math.pow

fun main() {
    val inputFilename = "day_14.txt"
    val lines = File("$resourcePath/$inputFilename").readLines()
    val regex = Regex("(\\d)+")
    val instructions = lines.map { line ->
        val tokens = line.split("=")
        if (tokens[0].trim() == "mask") {
            val mask = tokens[1].trim()
            Pair(null, mask)
        } else {
            val match = regex.find(tokens[0])
            val memAddress = match?.groupValues?.first()!!.toInt()
            val decValue = tokens[1].trim().toLong()
            Pair(memAddress, decValue.toString())
        }
    }
    println("${instructions.size} instructions")
    
    println("Part 1 answer: ${part1(instructions)}")

    println("Part 2 answer: ${part2(instructions)}")
}


private fun part2(instructions: List<Pair<Int?, String>>): Long {
    var mask = ""
    val memory = mutableMapOf<Long, Long>()
    instructions.forEach { (memAddress, writeValue) ->
        if (memAddress == null) {
            mask = writeValue
        } else {
            val binAddress = memAddress.toInt().toBinary()
                .padStart(mask.length, '0')
            val maskedAddress = mask.mapIndexed { index, char ->
                when {
                    char == '1' -> '1'
                    char == '0' -> binAddress[index]
                    else -> 'X'
                }
            }.joinToString("")
            determineAddresses(maskedAddress).forEach { address ->
                memory[address] = writeValue.toLong()
            }
        }
    }
//    println("Memory: ")
//    memory.forEach { (k, v) -> println("\t$k -> $v") }
    return memory.values.sum()
}

private fun determineAddresses(floatingAddress: String): List<Long> {
    val nrOfXs = floatingAddress.count { it == 'X' }
    val possibilities = pow(2.0, nrOfXs.toDouble()).toInt()

    return (0 until possibilities)
        .map {
            it.toBinary().padStart(nrOfXs, '0')
        }.map { value ->
            var newAddress = floatingAddress
            value.forEach { char ->
                newAddress = newAddress.replaceFirst('X', char)
            }
            newAddress.toDecimal()
        }
}


private fun part1(instructions: List<Pair<Int?, String>>): Long {
    var mask = ""
    val memory = instructions.mapNotNull { (memAdress, writeValue) ->
        if (memAdress == null) {
            mask = writeValue
            null
        } else {
            val binValue = writeValue.toInt().toBinary()
                .padStart(mask.length, '0')
            val newValue = binValue.mapIndexed { index, char ->
                if (mask[index] == 'X')
                    char
                else
                    mask[index]
            }.joinToString("")
            memAdress to newValue.toDecimal()
        }
    }.toMap()

    return memory.values.sum()
}
