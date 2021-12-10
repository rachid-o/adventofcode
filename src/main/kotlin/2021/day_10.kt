package `2021`

import java.io.File
import java.lang.IllegalArgumentException
import java.util.*

fun main() {
    val inputFilename = "day_10.txt"
    println("Reading file $resourcePath/$inputFilename")
    val input = File(resourcePath, inputFilename)
        .readLines()
        .map(String::toCharArray)
    println("input(${input.size}): ${input.map { it.joinToString("") }}")
    println("Answer 1: " + part1(input))
    println("Answer 2: " + part2(input))
}

private fun part1(input: List<CharArray>): Int {
    val answer = input.mapNotNull { line ->
        getFirstIllegalChar(line)?.let {
            when (it) {
                ')' -> 3
                ']' -> 57
                '}' -> 1197
                '>' -> 25137
                else -> null
            }
        }
    }.sum()
    println("Answer: $answer")
    check(answer == 278475)
    return answer
}

private fun part2(input: List<CharArray>): Long {
    val allScores = input.filter { line ->
        getFirstIllegalChar(line) == null
    }.map {
        val completion = autoComplete(it)
        completion.foldIndexed(0.toLong()) { index, acc, c ->
            val pointValue = when (c) {
                ')' -> 1
                ']' -> 2
                '}' -> 3
                '>' -> 4
                else -> 0 // Should never happen
            }
            acc * 5 + pointValue
        }
    }.sorted()
    val answer = allScores[allScores.size / 2]
    println("answer: $answer")
    check(answer == 3015539998)
    return answer
}

val charPairs = mapOf(
    '(' to ')',
    '[' to ']',
    '{' to '}',
    '<' to '>',
)
val openingChars = charPairs.keys.toSet()
val closingChars = charPairs.values.toSet()

fun getFirstIllegalChar(line: CharArray): Char? {
    val stack = Stack<Char>()
    line.forEach { c ->
        if (c in openingChars) {
            stack.push(c)
        } else if (c in closingChars) {
            val popped = stack.pop()
            if (charPairs[popped] != c) {
                // c does not match with an opening char!
                return c
            }
        } else {
            throw IllegalArgumentException("Unknown character: $c")
        }
    }
    // line is incomplete
    return null
}

fun autoComplete(line: CharArray): List<Char> {
    val stack = Stack<Char>()
    line.forEach { c ->
        if (c in openingChars) {
            stack.push(c)
        } else if (c in closingChars) {
            val popped = stack.pop()
            if (charPairs[popped] != c) {
                throw IllegalArgumentException("$c does not match with an opening char!")
            }
        } else {
            throw IllegalArgumentException("Unknown character: $c")
        }
    }
    return stack.indices.map {
        charPairs.getValue(stack.pop())
    }
}
