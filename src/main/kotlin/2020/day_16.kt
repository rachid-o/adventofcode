package `2020`

import utils.multiply
import java.io.File


fun main() {
    val inputFilename = "day_16.txt"
    val notes = parseFile("$resourcePath/$inputFilename")

    println("Answer part 1: " + part1(notes))
    println("Answer part 2: " + part2(notes))
}

private data class Notes(
    val rules: Map<String, List<IntRange>>,
    val myTicket: List<Int>,
    val nearbyTickets: List<List<Int>>,
) {
    fun valid(nr: Int) =
        rules.values.any { rule -> rule.any { range -> range.contains(nr) } }
}

private fun part1(notes: Notes): Int {
    val invalid = notes.nearbyTickets.flatMap { ticket ->
        val invalid = ticket.filterNot { notes.valid(it) }
        invalid
    }
    return invalid.sum()
}

private fun part2(notes: Notes): Long {
    val validTickets = notes.nearbyTickets.filterNot { ticket ->
        ticket.any { !notes.valid(it) }
    }
    val len = validTickets.first().size
    val rulesWithValidIndices: Map<String, MutableList<Int>> = notes.rules.map { (ruleName, rule) ->
        val validIndices = (0 until len)
            .filter { index ->
                validTickets.all { ticket ->  rule.any { it.contains(ticket[index]) }  }
            }.toMutableList()
        ruleName to validIndices
    }.toMap()

    val ruleWithIndex = rulesWithValidIndices.map {
        val indexEntry = rulesWithValidIndices.entries.find { (_, v) -> v.size == 1 }!!
        val index = indexEntry.value.first()
        rulesWithValidIndices.values.forEach { list -> list.remove(index) }
        indexEntry.key to index
    }.toMap()

    return ruleWithIndex
        .filter { it.key.startsWith("departure") }
        .map { notes.myTicket.get(it.value).toLong() }
        .multiply()
}

private fun parseFile(inputFile: String): Notes {
    println("Reading file $inputFile")
    val fileContents = File(inputFile).readText().split("\n\n")
    return Notes(
        parseRules(fileContents[0]),
        parseMyTickets(fileContents[1]),
        parseNearbyTickets(fileContents[2])
    )
}

private fun parseRules(strRules: String) = strRules.split("\n").map { line ->
    val kv = line.split(":")
    val type = kv[0].trim()
    val strRanges = kv[1].trim()
    val ranges = strRanges.split(" or ").map { strRange ->
        val (from, to) = strRange.split("-")
        IntRange(from.toInt(), to.toInt())
    }
    type to ranges
}.toMap()

private fun parseMyTickets(strRules: String) = strRules.split(":")[1].trim()
    .split(",")
    .map { it.toInt() }

private fun parseNearbyTickets(strRules: String) = strRules.split(":")[1].trim()
    .split("\n")
    .map { line ->
        line.split(",")
        .map { it.toInt() }
    }
