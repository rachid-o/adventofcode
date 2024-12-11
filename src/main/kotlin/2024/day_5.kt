package `2024`

import java.io.File

fun main() {
    val inputFilename = "day_5.txt"
    println("Reading file $resourcePath/$inputFilename")
    val (rules, updates) = parseInput(File(resourcePath, inputFilename))

    println("Rules: $rules")
    println("Updates: $updates\n")

    val answer1 = part1(rules, updates)
    println("Answer 1: $answer1")

    val answer2 = part2(rules, updates)
    println("Answer 2: $answer2")
    check(answer1 == 4766)
    check(answer2 == 6257)
}

private fun part1(rules: List<Pair<Int, Int>>, updates: List<List<Int>>): Int {
    return updates
        .filter { validUpdate(it, rules) }
        .sumOf { update ->
            val middle = (update.size - 1) / 2
            update[middle]
        }
}

private fun validUpdate(update: List<Int>, rules: List<Pair<Int, Int>>): Boolean {
    for (rule in rules) {
        if (rule.first !in update || rule.second !in update) {
            continue
        }
        val valid = update.indexOf(rule.first) < update.indexOf(rule.second)
        if (!valid) {
            return false
        }
    }
    return true
}

private fun part2(rules: List<Pair<Int, Int>>, updates: List<List<Int>>): Int {
    return updates
        .mapNotNull { fixInValidUpdate(it, rules) }
        .sumOf { update ->
            val middle = (update.size - 1) / 2
            update[middle]
        }
}

private fun fixInValidUpdate(update: List<Int>, rules: List<Pair<Int, Int>>): List<Int>? {
    if (validUpdate(update, rules)) {
        return null
    }
    val newUpdate = update.toMutableList()
    while (!validUpdate(newUpdate, rules)) {
        for (rule in rules) {
            if (rule.first !in update || rule.second !in update) {
                continue
            }
            val valid = newUpdate.indexOf(rule.first) < newUpdate.indexOf(rule.second)
            if (!valid) {
                val id1 = newUpdate.indexOf(rule.first)
                val id2 = newUpdate.indexOf(rule.second)
                newUpdate[id1] = rule.second
                newUpdate[id2] = rule.first
                if (validUpdate(newUpdate, rules)) {
                    return newUpdate
                }
            }
        }
    }
    return null
}

private fun parseInput(inputFile: File): Pair<List<Pair<Int, Int>>, List<List<Int>>> {
    val (strRules, strUpdates) = inputFile
        .readText().split("\n\n")
    val rules = strRules
        .split("\n")
        .map { line ->
            val (first, second) = line.split("|").map { it.trim().toInt() }
            first to second
        }
    val updates = strUpdates
        .split("\n")
        .map { line -> line.split(",").map { it.trim().toInt() } }
    return rules to updates
}
