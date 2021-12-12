package `2021`

import java.io.File

fun main() {
    val inputFilename = "day_12.txt"
    println("Reading file $resourcePath/$inputFilename")
    val input = File(resourcePath, inputFilename)
        .readLines()
        .map {
            val (from, to) = it.split("-")
            from to to
        }.toSet()
    println("input(${input.size}): $input")
    println("Answer 1: " + part1(input))
    println("Answer 2: " + part2(input))
}

private const val START = "start"
private const val END = "end"
private typealias Path = List<String>
private typealias Connection = Pair<String, String>

private fun part1(map: Set<Connection>): Int {
    val paths = findPaths(map, listOf(START), 1)
//    println("\nAll Paths:")
//    paths.forEach { println(it.joinToString(",")) }
    val answer = paths.size
    println("Answer: $answer")
//    check(answer == 10)
    check(answer == 5212)
    return answer
}

private fun part2(map: Set<Connection>): Int {
    val paths = findPaths(map, listOf(START), 2)
//    println("\nAll Paths:")
//    paths.forEach { println(it.joinToString(",")) }
    val answer = paths.size
    println("Answer: $answer")
//    check(answer == 36)
    check(answer == 134862)
    return answer
}

fun findPaths(map: Set<Connection>, currentPath: Path, visitSmallCaves: Int): List<Path> {
    // Check if there is more than 1 small cave which is visited twice
    val smallCavesVisitedTwice = currentPath.groupingBy { it }
        .eachCount()
        .filterKeys { it.isSmall() }
        .filterValues { it > 1 }
        .count()
    if(smallCavesVisitedTwice > 1) {
        return emptyList()
    }

    val currentCave = currentPath.last()
    if(currentCave == END) {
        return listOf(currentPath)
    }
    val nextCaves = map
        .filter { it.first == currentCave || it.second == currentCave }
        .flatMap { listOf(it.first, it.second) }
        .filterNot { it == currentCave }
        .filterNot {  cave ->
            cave.isSmall() && currentPath.count { it == cave } >= visitSmallCaves
        }
        .filterNot { cave ->
            cave == START
        }
    return nextCaves.flatMap { cave ->
        findPaths(map, currentPath + cave, visitSmallCaves)
    }
}

private fun String.isSmall() = this.toCharArray().all { it.isLowerCase() }
