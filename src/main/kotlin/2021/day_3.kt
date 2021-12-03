package `2021`

import utils.toDecimal
import java.io.File

fun main() {
    val inputFilename = "day_3.txt"
    println("Reading file $resourcePath/$inputFilename")
    val report = File(resourcePath, inputFilename)
        .readLines()

    println("Report size:  ${report.size}")

    println("Answer 1: " + part1(report))
    println("Answer 2: " + part2(report))
}

private fun part1(report: List<String>): Long {
    var gammaRate = ""
    var epsilonRate = ""
    for (i in 0 until report.first().length) {
        val zeroes = report.count { it[i] == '0' }
        val ones = report.size - zeroes
        gammaRate += if (zeroes > ones) "0" else "1"
        epsilonRate += if (zeroes > ones) "1" else "0"
    }
    println("gammaRate:   $gammaRate - " + gammaRate.toDecimal())
    println("epsilonRate: $epsilonRate - " + epsilonRate.toDecimal())

    val powerConsumption = gammaRate.toDecimal() * epsilonRate.toDecimal()
    check(powerConsumption == 3148794L)
    return powerConsumption
}

private fun part2(report: List<String>): Long {
    val oxygenRating: Long = calcOxygenRating(report)
    val co2Rating: Long = calcCO2Rating(report)

//    check(oxygenRating * co2Rating == 230L)   // Example
    val lifeSupportRating = oxygenRating * co2Rating
    check(lifeSupportRating == 2795310L)
    return lifeSupportRating
}

fun calcOxygenRating(report: List<String>): Long {
    var oxygenReport = report
    for (i in 0 until report.first().length) {
        val zeroes = oxygenReport.count { it[i] == '0' }
        val ones = oxygenReport.count { it[i] == '1' }
        val filter = if (ones >= zeroes) '1' else '0'
        oxygenReport = oxygenReport.filter { it[i] == filter }
        if (oxygenReport.size == 1) {
            return oxygenReport.first().toDecimal()
        }
    }
    return -1
}

fun calcCO2Rating(report: List<String>): Long {
    var co2Report = report
    for (i in 0 until report.first().length) {
        val zeroes = co2Report.count { it[i] == '0' }
        val ones = co2Report.count { it[i] == '1' }
        val filter = if (zeroes <= ones) '0' else '1'
        co2Report = co2Report.filter { it[i] == filter }
        if (co2Report.size == 1) {
            return co2Report.first().toDecimal()
        }
    }
    return -1
}

