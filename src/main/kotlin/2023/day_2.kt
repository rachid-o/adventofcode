package `2023`

import java.io.File

fun main() {
    val inputFilename = "day_2.txt"
    println("Reading file $resourcePath/$inputFilename")
    val input = File(resourcePath, inputFilename)
        .readLines()
    val games = parseInput(input)

    val answer1 = part1(games)
    println("Answer 1: $answer1")

    val answer2 = part2(games)
    println("Answer 2: $answer2")
    check(answer1 == 2156)
    check(answer2 == 66909)
}


private fun part1(games: List<Game>): Int {
    return games.filter { isGamePossible(it, 12, 13, 14) }.sumOf { it.id }
}

private fun isGamePossible(game: Game, red: Int, green: Int, blue: Int): Boolean {
    return game.sets.all { set ->
        set.getOrDefault("red", 0) <= red &&
                set.getOrDefault("green", 0) <= green &&
                set.getOrDefault("blue", 0) <= blue
    }
}

private data class Game(val id: Int, val sets: List<Map<String, Int>>)

private fun parseInput(strGames: List<String>) = strGames.map { strGame ->
        val parts = strGame.split(": ")
        val id = parts[0].removePrefix("Game ").toInt()
        val sets = parts[1].split(";").map { setString ->
            setString.trim().split(",").associate { cubeString ->
                val cubeParts = cubeString.trim().split(" ")
                cubeParts[1] to cubeParts[0].toInt()
            }.toMap()
        }
        Game(id, sets)
    }

// Part 2

private fun part2(games: List<Game>): Int {
    return games.sumOf { powerOfMinCubes(minCubesForGame(it)) }
}

private fun minCubesForGame(game: Game): Map<String, Int> {
    return mapOf(
        "red" to (game.sets.map { it.getOrDefault("red", 0) }.maxOrNull() ?: 0),
        "greens" to (game.sets.map { it.getOrDefault("green", 0) }.maxOrNull() ?: 0),
        "blues" to (game.sets.map { it.getOrDefault("blue", 0) }.maxOrNull() ?: 0),
    )
}

private fun powerOfMinCubes(cubes: Map<String, Int>): Int {
    return cubes.values.reduce(Int::times)
}
