package `2021`

import java.io.File

fun main() {
    val inputFilename = "day_14.txt"
    println("Reading file $resourcePath/$inputFilename")
    val (template, rules) = parseFile(inputFilename)
    println("template: $template")
    println("rules(${rules.size}): $rules")
    println("Answer 1: " + part1(template, rules))
    println("Answer 2: " + part2(template, rules))
}

private fun part1(template: String, rules: Map<String, String>): Int {
    var newTemplate = template
    repeat(10) {
        newTemplate = nextStep(newTemplate, rules)
        println("After step ${it+1}: ${newTemplate.length}")
    }

    val counts = newTemplate.groupingBy { it }.eachCount()
    val mostCommon = counts.maxOf { it.value }
    val leastCommon = counts.minOf { it.value }
    println("mostCommon: $mostCommon")
    println("leastCommon: $leastCommon")


    val answer = mostCommon - leastCommon
    println("Answer: $answer")
//    check(answer == 1588)
    check(answer == 2590)
    return answer
}

private fun part2(template: String, rules: Map<String, String>): Long {
    // TODO: Make it work
    var newTemplate = template
    repeat(40) {
        newTemplate = nextStep(newTemplate, rules)
//        println("After step ${it+1}: $newTemplate")
        println("After step ${it+1}: ${newTemplate.length}")
    }

    val counts = mutableMapOf<Char, Long>()
    newTemplate.forEach { char ->
        counts.putIfAbsent(char, 0)
        counts[char] = counts[char]!! + 1
    }

    val mostCommon = counts.maxOf { it.value }
    val leastCommon = counts.minOf { it.value }
    println("mostCommon: $mostCommon")
    println("leastCommon: $leastCommon")


    val answer = mostCommon - leastCommon
    println("Answer: $answer")
    check(answer == 2188189693529)
    return answer
}

private fun nextStep(
    template: String,
    rules: Map<String, String>
) = template.substring(0, 1) + template
    .windowed(2, 1)
    .map {
        var newPair = rules[it]
        if (newPair == null) {
            newPair = it.substring(1)
        } else {
            newPair = newPair.substring(1)
        }
        newPair
    }.joinToString("")

private fun parseFile(inputFilename: String): Pair<String, Map<String, String>> {
    val (template, strPairs) = File(resourcePath, inputFilename)
        .readText().split("\n\n")
    val rules = strPairs.split("\n")
        .associate {
            val (pair, element) = it.split(" -> ")
            val replacement = pair[0] + element + pair[1]
            pair to replacement
        }
    return template to rules
}

