package `2020`

import java.io.File


fun main() {
    val inputFilename = "day_8.txt"
    val instructions = parseFile("$resourcePath/$inputFilename")
    println("nr of instructions in file:  ${instructions.size}")

    part1(instructions)

    part2(instructions)
}

private fun part1(instructions: List<Instruction>) {
    println("Part 1: ")
    var accumulator = 0
    var pointer = 0
    val processedInstructions = mutableSetOf<Int>()
    while(!processedInstructions.contains(pointer) && pointer < instructions.size) {
        val currInstruction = instructions.get(pointer)
        processedInstructions.add(pointer)
        when(currInstruction.operation) {
            Operation.acc -> {
                accumulator += currInstruction.argument
                pointer++
            }
            Operation.jmp -> {
                pointer += currInstruction.argument
            }
            Operation.nop -> {
                pointer++
            }
        }
    }

    println("\t accumulator: $accumulator ")
}

private fun part2(instructions: List<Instruction>) {
    println("Part 2: ")

    if(terminates(instructions)) {
        println("Given instrutions already terminate correctly")
        return
    }
    var accumulator = 0
    var pointer = 0
    val processedInstructions = mutableListOf<Int>()
    while(pointer < instructions.size) {
        val currInstruction = instructions.get(pointer)
        if(processedInstructions.contains(pointer)) {
            println("Already processed $currInstruction on pointer $pointer ")
            break
        }
        processedInstructions.add(pointer)
        when(currInstruction.operation) {
            Operation.acc -> {
                accumulator += currInstruction.argument
                pointer++
            }
            Operation.jmp -> {
                pointer += currInstruction.argument
            }
            Operation.nop -> {
                pointer++
            }
        }
    }

    val instructionsToModify = processedInstructions
        .filter { instructions.get(it).operation != Operation.acc }

    println("Trying to see if it terminates when we modify the ${instructionsToModify.size} potential looping instructions")
    instructionsToModify.forEach { i ->
        val oldIns = instructions.get(i)
        val newIns = if(oldIns.operation == Operation.acc) {
            oldIns.copy(operation = Operation.nop)
        } else {
            oldIns.copy(operation = Operation.acc)
        }
        val newInstructions = instructions.toMutableList()
        newInstructions.set(i, newIns)

        if(terminates(newInstructions)) {
            println("The instruction on line ${i+1} must be replaced!")
        }

    }
}


private fun terminates(instructions: List<Instruction>): Boolean {
    var accumulator = 0
    var pointer = 0
    val processedInstructions = mutableSetOf<Int>()
    while(pointer < instructions.size) {
        val currInstruction = instructions.get(pointer)
        when(currInstruction.operation) {
            Operation.acc -> {
                accumulator += currInstruction.argument
                pointer++
            }
            Operation.jmp -> {
                pointer += currInstruction.argument
            }
            Operation.nop -> {
                pointer++
            }
        }
        if(processedInstructions.contains(pointer)) {
            return false
        }
        processedInstructions.add(pointer)
    }
    return true
}


private enum class Operation {
    acc,
    jmp,
    nop
}

private data class Instruction(val operation: Operation, val argument: Int) {
    override fun toString() = "${operation} ${if(argument> 0) "+" else ""}$argument"
}

private fun parseFile(inputFile: String): List<Instruction> {
    println("Reading file $inputFile")
    return File(inputFile).readLines()
        .map {
            val (strOp, strArg) = it.split(" ")
            Instruction(Operation.valueOf(strOp), strArg.toInt())
        }
}
