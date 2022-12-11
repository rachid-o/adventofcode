package `2022`

import utils.multiply
import java.io.File

fun main() {
    val inputFilename = "day_11.txt"
    println("Reading file $resourcePath/$inputFilename")
    val input1 = parseInput(File(resourcePath, inputFilename))
    val input2 = parseInput(File(resourcePath, inputFilename))

    println("Input:")
    input1.forEachIndexed { nr, monkey -> println("$nr -> $monkey") }
    println()

    val answer1 = calcMonkeyBusiness(input1, 20, 3)
    println("Answer 1: $answer1")
    val answer2 = calcMonkeyBusiness(input2, 10_000, 1)
    println("Answer 2: \n$answer2")
}

private fun calcMonkeyBusiness(monkeys: List<Monkey>, nrOfRounds: Int, divideBy: Long): Long {
    repeat(nrOfRounds) {
        doRound(monkeys, divideBy)
    }

    println("\nAfter $nrOfRounds rounds: ")
    monkeys.forEachIndexed { nr, m -> println("Monkey $nr inspected items ${m.itemsInspected}") }

    return monkeys
        .map { it.itemsInspected.toLong() }
        .sortedDescending()
        .take(2)
        .multiply()
}

private fun doRound(monkeys: List<Monkey>, dividedForRelief: Long) {
    val divLimit = monkeys.map { it.divisibleBy }.multiply()

    monkeys.forEach { m ->
        m.items.forEach { worry ->
            m.itemsInspected++
            val new = when(m.op.first) {
                Operator.ADD -> worry + substituteVar(m.op.second, worry)
                Operator.MUL -> worry * substituteVar(m.op.second, worry)
            } / dividedForRelief

            val nextMonkeyNr = if(new % m.divisibleBy == 0L) {
                m.nextMonkeyTrue
            } else {
                m.nextMonkeyFalse
            }
            monkeys[nextMonkeyNr].items.add(new % divLimit)
        }
        m.items.clear()
    }
}

fun substituteVar(variable: String, worry: Long) =
    if(variable == "old") {
        worry
    } else {
        variable.toLong()
    }

private fun parseInput(file: File) =
    file.readText()
        .split("\n\n")
        .map{ parseMonkey(it) }

private fun parseMonkey(strMonkey: String): Monkey {
    val attributes = strMonkey.split("\n")
    val startingItems = attributes[1].split(":")[1].split(",").map { it.trim().toLong() }
    val operatorTokens = attributes[2].split(" ")
    val operator = Operator.fromString(operatorTokens[operatorTokens.lastIndex - 1])
    val variable = operatorTokens.last()
    val operation = operator to variable

    return Monkey(
        startingItems.toMutableList(),
        operation,
        attributes[3].getLastAsInt(),
        attributes[4].getLastAsInt(),
        attributes[5].getLastAsInt()
    )
}

private fun String.getLastAsInt() = split(" ").last().toInt()

private data class Monkey(
    val items: MutableList<Long>,
    val op: Pair<Operator, String>,
    val divisibleBy: Int,
    val nextMonkeyTrue: Int,
    val nextMonkeyFalse: Int,
    var itemsInspected: Int = 0
)

private enum class Operator(val code: String) {
    MUL("*"),
    ADD("+");

    companion object {
        fun fromString(code: String): Operator = values().firstOrNull { it.code == code }!!
    }
}
