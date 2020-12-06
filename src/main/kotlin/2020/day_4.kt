package `2020`

import java.io.File


fun main() {
    val inputFilename = "day_4.txt"
    val passports = parseFile("$resourcePath/$inputFilename")
//    passports.forEach { println(it)}

    println("nr of passports in file:  ${passports.size}")

    val requiredFields = setOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
    part1(passports, requiredFields)

    val validEyeColors = setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
    part2(passports, requiredFields, validEyeColors)
}

private fun part2(passports: List<Map<String, String>>, requiredFields: Set<String>, validEyeColors: Set<String>) {
    println("Part 2: ")
    val validPassports = passports
        .filter { it.keys.containsAll(requiredFields) }
        .filter { isValid(it, validEyeColors) }

//    validPassports.forEach { println(it) }

    println("Valid passports: ${validPassports.size}")
}

private fun isValid(passport: Map<String, String>, validEyeColors: Set<String>): Boolean {
    try {
        val byr = passport["byr"]!!.toInt()
        if(byr < 1920 || byr > 2002) return false

        val iyr = passport["iyr"]!!.toInt()
        if(iyr < 2010 || iyr > 2020) return false

        val eyr = passport["eyr"]!!.toInt()
        if(eyr < 2020 || eyr > 2030) return false

        val hgt = passport["hgt"]!!
        if(!(hgt.contains("cm") || hgt.contains("in"))) return false
        val height = hgt.substring(0, hgt.length-2).toInt()
        val metric = hgt.substring(hgt.length-2, hgt.length)
        if(metric == "cm" && (height < 150 || height > 193) ) return false
        if(metric == "in" && (height < 59 || height > 76) ) return false

        val hcl = passport["hcl"]!!
        if(!hcl.startsWith("#") || hcl.length != 7) return false
        val color = hcl.substring(1, hcl.length)
        if(!"[0-9a-f]{6}".toRegex().matches(color)) return false

        val ecl = passport["ecl"]!!
        if(!validEyeColors.contains(ecl)) return false

        val pid = passport["pid"]!!
        if(!"[0-9]{9}".toRegex().matches(pid)) return false

        return true
    } catch (e: Exception) {
        println("ERROR: Could not validate passport: $passport   -   ${e.message}")
        e.printStackTrace()
        return false
    }
}


private fun part1(passports: List<Map<String, String>>, requiredFields: Set<String>) {
    println("Part 1: ")
    val validPassports = passports.count { p ->
        p.keys.containsAll(requiredFields)
    }
    println("Passports with all fields present: $validPassports")
}



private fun parseFile(inputFile: String): List<Map<String, String>> {
    println("Reading file $inputFile")
    val fileContents = File(inputFile).readText()
    val txtPassports = fileContents.split("\n\n")
        .map { it.replace("\n", " ").trim() }
    val passports = txtPassports.map {
        val fields = it.split(" ")
        fields.map {
            val pair = it.split(":")
            pair[0] to pair[1]
        }.toMap()
    }
    return passports
}
