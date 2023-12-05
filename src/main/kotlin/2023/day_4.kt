package `2023`

import java.io.File
import kotlin.math.pow

fun main() {
    val inputFilename = "day_4.txt"
    println("Reading file $resourcePath/$inputFilename")
    val cards = parseInput(File(resourcePath, inputFilename).readLines())

    val answer1 = part1(cards.values.toList())
    println("Answer 1: $answer1")

    check(answer1 == 33950)
}

private fun part1(cards: List<Card>) = cards
    .sumOf { card ->
        val myWinNumbers = card.getMatchingNumbers()
        val score = if (myWinNumbers > 0)
            2.0.pow(myWinNumbers - 1).toInt()
        else
            0
        score
    }


private data class Card(val nr: Int, val numbersWin: Set<Int>, val numbersMe: Set<Int>, var amount: Int = 1) {
    fun getMatchingNumbers() = numbersMe.count { me -> numbersWin.any { win -> me == win } }
}

private fun parseInput(lines: List<String>): Map<Int, Card> {
    return lines.associate { line ->
        val (strCard, strNumbers) = line.split(":")
        val card = strCard.split(" ").last().trim().toInt()
        val (strNumbersWin, strNumbersMe) = strNumbers.split("|")
        val numbersWin = strNumbersWin.trim().split(" ")
            .mapNotNull {
                if (it.isEmpty())
                    null
                else
                    it.trim().toInt()
            }.toSet()
        val numbersMe = strNumbersMe.trim().split(" ")
            .mapNotNull {
                if (it.isEmpty())
                    null
                else
                    it.trim().toInt()
            }.toSet()
        card to Card(card, numbersWin, numbersMe)
    }
}