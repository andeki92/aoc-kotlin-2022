#!/usr/bin/env kotlin


import java.io.File

fun createDay(day: Int, dryRun: Boolean = true) {
    val paddedDay = "%02d".format(day)
    val template = """
package solutions

import models.InputProvider

context (InputProvider)
class Day$paddedDay : Day($day, 2022, "") {

    override fun part1(): Int {
        TODO()
    }

    override fun part2(): Int {
        TODO()
    }
}


fun main() {
    test<Day$paddedDay>(null, null)
    solve<Day$paddedDay>()
}
""".trimIndent()

    val solutionFile = File("src/main/kotlin/solutions/Day$paddedDay.kt")
    val testInputFile = File("src/main/resources/day$paddedDay.example.txt")
    val inputFile = File("src/main/resources/day$paddedDay.txt")

    val newFiles = listOf(solutionFile, testInputFile, inputFile).filterNot(File::exists)

    if (dryRun && newFiles.isNotEmpty()) {
        println("would create new files:")
        newFiles.forEach { println("- $it") }
        return
    }

    newFiles.forEach(File::createNewFile)

    if (solutionFile.readText().isEmpty()) {
        solutionFile.writeText(template)
    }
}


main(*args)

fun main(vararg args: String) {
    require(args.isNotEmpty()) { "version argument required" }

    val dayArg = args.first().toInt()
    require(dayArg in 1..24) { "day should be between 1 and 24" }

    createDay(dayArg, dryRun = false)
}