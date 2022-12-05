package `2022`

import java.io.File
import java.util.*

fun main() {
    val inputFilename = "day_5.txt"
    println("Reading file $resourcePath/$inputFilename")
    val (strInput, strInstructions) = File(resourcePath, inputFilename).readText().split("\n\n")
    val instructions = parseInstructions(strInstructions)

    val input1 = parseInput(strInput)
    println("input: $input1")
    println("instructions: $instructions")

    val answer1 = part1(input1, instructions)
    println("Answer 1: $answer1")

    val input2 = parseInput(strInput)
    val answer2 = part2(input2, instructions)
    println("Answer 2: $answer2")
    check(answer1 == "FWNSHLDNZ")
    check(answer2 == "RNRGDNFQG")
}

const val amountIndex = 0
const val fromIndex = 1
const val toIndex = 2

private fun part1(input: Map<Int, Stack<Char>>, instructions: List<List<Int>>): String {

    instructions.forEach { ins ->
        val amount = ins[amountIndex]
        val from = ins[fromIndex]
        val to = ins[toIndex]
        repeat(amount) {
            val crate = input[from]!!.pop()
            input[to]!!.push(crate)
        }
    }

    return input
        .map { (_, stack) -> stack.peek() }
        .joinToString("")
}

private fun part2(input: Map<Int, Stack<Char>>, instructions: List<List<Int>>): String {
    instructions.forEach { ins ->
        val amount = ins[amountIndex]
        val to = ins[toIndex]
        (0 until amount).map {
            val from = ins[fromIndex]
            input[from]!!.pop()
        }
        .reversed()
        .forEach { crate ->
            input[to]!!.push(crate)
        }
    }

    return input
        .map { (_, stack) -> stack.peek() }
        .joinToString("")
}


fun parseInstructions(strInstructions: String): List<List<Int>> {
    return strInstructions
        .split("\n")
        .map { line ->
            line
                .split(" ")
                .mapNotNull { token ->
                    if(token.toIntOrNull() == null) {
                        null
                    } else {
                        token.toInt()
                    }
                }
        }
}

fun parseInput(strInput: String): Map<Int, Stack<Char>> {

    val lines = strInput.split("\n")
        .reversed()

    val stackNumbers = lines[0]
        .split("")
        .mapIndexedNotNull { index, token ->
            if (token.toIntOrNull() == null) {
                null
            } else {
                token.toInt() to index - 1
            }
        }.associate { (stackNr, stringIndex) ->
            val stack = Stack<Char>()
            lines.subList(1, lines.size)
                .forEach { line ->
                    val crate = line[stringIndex]
                    if (crate != ' ') {
                        stack.push(crate)
                    }
                }
            stackNr to stack
        }
    return stackNumbers
}

