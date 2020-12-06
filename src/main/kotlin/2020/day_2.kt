package `2020`

import java.io.File


fun main() {
    val inputFilename = "day_2.txt"
    println("Reading file $resourcePath/$inputFilename")
    val lines = File("$resourcePath/$inputFilename")
            .readLines()

    println("file size:  ${lines.size}")

    part1(lines)
    part2(lines)
}

private fun part1(lines: List<String>) {
    println("Part 1: ")
//    lines.forEach { println(it) }
    val parsedLines = lines.map { parseLine(it) }
    println("Valid passwords part 1: " + parsedLines.count { it.isValidPart1() })
}

private fun part2(lines: List<String>) {
    println("Part 2: ")
    val parsedLines = lines.map { parseLine(it) }
//    parsedLines.forEach { println(it) }
    println("Valid passwords part 2: " + parsedLines.count { it.isValidPart2() })
//    parsedLines.filterNot { it.isValidPart2() }.forEach {
//        println("$it  (${it.password.length})")
//    }
//    println("Valid passwords part 2: " + )
}



private data class PasswordAndPolicy(val range: IntRange, val letter: Char, val password: String) {
    fun isValidPart1(): Boolean {
        val count = password.count { letter == it }
        return count in range
    }
    fun isValidPart2(): Boolean {
        if(password.length < range.last) {
            return false
        }
        return (password[range.first-1] == letter && password[range.last-1] != letter) ||
                password[range.first-1] != letter && password[range.last-1] == letter
    }

    override fun toString() = "$letter $range\t $password"
}

private fun parseLine(textLine: String): PasswordAndPolicy {
    val tokens = textLine.split(":")
    if(tokens.size < 2) {
        throw IllegalArgumentException("incorrect input: $textLine")
    }
    val password = tokens[1].trim()

    val policy = tokens.first().split(" ")
    if(policy.size < 2) {
        throw IllegalArgumentException("incorrect range input: $textLine")
    }
    val letter = policy[1].toCharArray().first()

    val range = policy.first().split("-").map { it.toInt()}
    if(range.size < 2) {
        throw IllegalArgumentException("incorrect range input: $textLine")
    }
    return PasswordAndPolicy(IntRange(range[0], range[1]), letter,  password)
}

