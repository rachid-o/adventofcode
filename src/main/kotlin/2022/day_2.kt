package `2022`

import java.io.File

/*
1 = A = X = Rock     - Lose
2 = B = Y = Paper    - Draw
3 = C = Z = Scissors - Win
*/

fun main() {
    val inputFilename = "day_2.txt"
    println("Reading file $resourcePath/$inputFilename")
    val input = File(resourcePath, inputFilename)
        .readLines()
        .map {
            val (opponent, me) = it.split(" ")
            opponent to me
        }

    println("Input: $input")
    val input1 = input.map { (opponent, me) ->
            Play.fromString(opponent) to Play.fromString(me)
        }
    val answer1 = part1(input1)
    println("Answer 1: $answer1")

    val input2 = input.map { (opponent, expectedOutcome) ->
            Play.fromString(opponent) to Outcome.fromString(expectedOutcome)
        }
    val answer2 = part2(input2)
    println("Answer 2: $answer2")
}

private fun part1(input: List<Pair<Play, Play>>) = input.sumOf { (opponent, me) ->
        me.outcomeAgainst(opponent).score + me.score
    }

private fun part2(input: List<Pair<Play, Outcome>>) = input.sumOf { (opponent, expectedOutcome) ->
        expectedOutcome.score + opponent.playNeededForOutcome(expectedOutcome).score
    }

private enum class Outcome(val score: Int) {
    Win(6),
    Lose(0),
    Draw(3);

    companion object {
        fun fromString(outcomeCode: String): Outcome {
            return when(outcomeCode) {
                "X" -> Lose
                "Y" -> Draw
                "Z" -> Win
                else -> throw IllegalArgumentException("$outcomeCode is invalid")
            }
        }
    }
}

private enum class Play(val playCode: Char, val score: Int) {
    Rock('R', 1) {
        override fun outcomeAgainst(other: Play): Outcome {
            return when (other) {
                Rock -> Outcome.Draw
                Paper -> Outcome.Lose
                Scissors -> Outcome.Win
            }
        }
    },
    Paper('P', 2) {
        override fun outcomeAgainst(other: Play): Outcome {
            return when (other) {
                Rock -> Outcome.Win
                Paper -> Outcome.Draw
                Scissors -> Outcome.Lose
            }
        }
    },
    Scissors('S', 3) {
        override fun outcomeAgainst(other: Play): Outcome {
            return when (other) {
                Rock -> Outcome.Lose
                Paper -> Outcome.Win
                Scissors -> Outcome.Draw
            }
        }
    };

    override fun toString() = playCode.toString()
    abstract fun outcomeAgainst(other: Play): Outcome

    /**
     * return the Play the other should choose to get given outcome
     */
    fun playNeededForOutcome(expectedOutcome: Outcome) =
        values().first { it.outcomeAgainst(this) == expectedOutcome }

    companion object {
        fun fromString(playCode: String): Play {
            return when(playCode) {
                "A" -> Rock
                "B" -> Paper
                "C" -> Scissors
                "X" -> Rock
                "Y" -> Paper
                "Z" -> Scissors
                else -> throw IllegalArgumentException("$playCode is invalid")
            }
        }
    }
}
