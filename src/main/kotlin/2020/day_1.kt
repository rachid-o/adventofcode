package `2020`

import java.io.File

const val inputFilename = "day1.txt"

fun main() {
    println("Reading file $resourcePath/$inputFilename")
    val expenseReport = File("$resourcePath/$inputFilename")
        .readLines().map { it.toInt() }

    println("expenseReport size:  ${expenseReport.size}")

    expenseReport.forEach { first ->
        expenseReport.forEach { second ->
            if(first + second == 2020) {
                println("$first * $second = ${first*second}")
            }
        }
    }


}
