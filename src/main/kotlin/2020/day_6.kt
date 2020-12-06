package `2020`

import java.io.File


fun main() {
    val inputFilename = "day_6.txt"
    val groups = parseFile("$resourcePath/$inputFilename")
    println("nr of groups in file:  ${groups.size}")

    part1(groups)

    part2(groups)
}

private fun part1(groups: List<String>) {
    println("Part 1: ")
    val totalSum = groups.map {
        it.replace("\n", "").trim().toCharArray().toSet().size
    }.sum()

    println("Total sum: $totalSum")
}

private fun part2(groups: List<String>) {
    println("Part 2: ")

    // For each letter, +1 if this letter is included each sub group
    val totalSum = groups.map { group ->
        val persons = group.split("\n")
        val groupLetters = group.replace("\n", "").trim().toCharArray().toSet()

        val groupSum = groupLetters.map { groupLetter ->
            val pc = persons.count { p -> p.contains(groupLetter) }
            if(pc == persons.size) 1 else 0
        }.sum()
        println("$group \n$groupSum \n--------------------")
        groupSum
    }.sum()

    println("Total sum: $totalSum")
}



private fun parseFile(inputFile: String): List<String> {
    println("Reading file $inputFile")
    val fileContents = File(inputFile).readText()
    return fileContents.split("\n\n").map { it.trim() }
}
