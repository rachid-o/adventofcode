package `2022`

import java.util.*
import java.io.File as JavaIoFile

fun main() {
    val inputFilename = "day_7.txt"
    println("Reading file $resourcePath/$inputFilename")
    val input = parseInput(JavaIoFile(resourcePath, inputFilename))

    println("input:")
    println(input.getContents())

    val answer1 = part1(input)
    println("Answer 1: $answer1")

    val answer2 = part2(input)
    println("Answer 2: $answer2")
}

private fun part1(input: Dir): Long {
    println("${input.getAllDirs().size}  dirs: ${input.getAllDirs()}")
    return input.getAllDirs()
        .filter { it.getSize() <= 100000L}
        .sumOf { it.getSize() }
}
private fun part2(input: Dir): Long {
    val totalSpace  = 70000000L
    val spaceNeeded = 30000000L
    val unused = totalSpace - input.getSize()
    return input.getAllDirs()
        .filter { it.getSize() + unused >= spaceNeeded }
        .minByOrNull { it.getSize() }!!
        .getSize()
}

private fun parseInput(inputFile: JavaIoFile): Dir {
    val lines = inputFile.readLines()
        .map { it.split(" ") }
    println("parseInput: $lines")
    var currentLineNr = 0
    val currentDir = Stack<Dir>()
    while(currentLineNr < lines.size) {
        val line = lines[currentLineNr]
        if(line[0] == "$") {
            val cmd = line[1]
            if(cmd == "cd") {
                val arg = line[2]
                if(arg == "/") {
                    currentDir.push(Dir(arg))
                } else if(arg == "..") {
                    currentDir.pop()
                } else {
                    val nextDir = currentDir.peek().dirs.find { it.name == arg }!!
                    currentDir.push(nextDir)
                }
            } else if(cmd == "ls") {
                val subList = lines.drop(currentLineNr+1).takeWhile { it[0] != "$"}
                ls(subList, currentDir.peek())
                currentLineNr += subList.size
            }
        }
        currentLineNr++
    }

    return currentDir.firstElement()
}

private fun ls(lines: List<List<String>>, currentDir: Dir) {
    lines.forEachIndexed { index, line ->
        val cmd = line[0]
        when (cmd) {
            "dir" -> {
                val name = line[1]
                currentDir.dirs.add(Dir(name))
            }
            else -> {
                val size = line[0].toLong()
                val name = line[1]
                currentDir.files.add(File(name, size))
            }
        }
    }
}


data class Dir(val name: String, val dirs: MutableList<Dir> = mutableListOf(), val files: MutableList<File> = mutableListOf()) {
    override fun toString() = name

    fun getContents(dir: Dir = this, ident: String = ""): String {
        var retval = "$ident- ${dir.name} (dir) - ${dir.getSize()}\n"
        retval += dir.dirs.map { subDir -> getContents(subDir, "$ident  ") }.joinToString("\n")
        if(dir.dirs.isNotEmpty()) {
            retval += "\n"
        }
        retval += dir.files.map { file -> "$ident  - $file" }.joinToString("\n")
        return retval
    }

    fun getSize(): Long = files.sumOf { it.size } + dirs.sumOf { subDir -> subDir.getSize() }

    fun getAllDirs(): List<Dir> {
        return listOf(this) + dirs.flatMap { subDir -> subDir.getAllDirs() }
    }
}

data class File(val name: String, val size: Long) {
    override fun toString(): String {
        return "$name (file, size=$size)"
    }
}
