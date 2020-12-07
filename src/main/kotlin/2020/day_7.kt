package `2020`

import java.io.File


fun main() {
    val inputFilename = "day_7.txt"
    val lines = parseFile("$resourcePath/$inputFilename")
    println("nr of lines in file:  ${lines.size}")

    part1(lines)

    part2(lines)
}

fun part1(containers: Set<Container>) {
    println("Part 1: ")

    val bag = "shiny gold"
    val searchResult = searchContainersFor(containers, bag)
    val answer = searchResult.size

    println("How many colors can contain '$bag': $answer")
}

private fun part2(containers: Set<Container>) {
    println("Part 2: ")
    val bag = "shiny gold"
    val answer = requiredBags(containers, bag)

    println("'$bag' must contain $answer other bags")
}


private fun requiredBags(containers: Set<Container>, bag: String): Int {
    val subSet = containers
        .filter { it.edge.first == bag }
        .toSet()
    if(subSet.isEmpty()) return 0

    return subSet.map {
            requiredBags(containers, it.edge.second) * it.amount + it.amount
        }.sum()
}


fun searchContainersFor(containers: Set<Container>, bag: String): Set<String> {
    val subSet = containers
        .filter { it.edge.second == bag }
        .map { it.edge.first }
        .toSet()
    return subSet + subSet.flatMap { searchContainersFor(containers, it) }.toSet()
}

data class Container(val edge: Pair<String, String>, val amount: Int) {
    override fun toString() = "${edge.first} __${amount}__> ${edge.second}"
}


private fun parseFile(inputFile: String): Set<Container> {
    println("Reading file $inputFile")
    return File(inputFile).readLines().flatMap {
            val (strBag, strContainers) = it.split("contain")
            val (bagType, bagColor) = strBag.split(" ")
            val bag1 = "$bagType $bagColor"
            val containers = strContainers.split(",")
                .map { it
                    .replace(".", "")
                    .replace("bags", "")
                    .replace("bag", "")
                    .trim()
                }
                .toSet()
                .filterNot { it.contains("no other") }
                .map {
                    val amount = it.substring(0, 1).toInt()
                    val bag2 = it.substring(2, it.length)
                    Container(Pair(bag1, bag2), amount)
                }
                .toSet()
            containers
        }.toSet()
}
