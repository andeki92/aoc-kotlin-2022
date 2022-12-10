package solutions

import models.InputProvider

context (InputProvider)
class Day10 : Day(10, 2022, "Cathode-Ray Tube") {

    private val instructions: List<String> = input

    private val signals = instructions.fold(listOf(0 to 1)) { acc, instruction ->
        val cycle = acc.last().first
        val registryValue = acc.last().second

        if (instruction.take(4) == "noop") {
            acc.plus(cycle + 1 to registryValue)
        } else {
            acc.plus(cycle + 2 to registryValue + (instruction.drop(5).toInt()))
        }
    }

    override fun part1(): Int {
        return (20..220 step 40).sumOf { cycle ->
            signals.last { it.first < cycle }.second * cycle
        }
    }

    override fun part2(): String {
        return (0 until 240).chunked(40).map { cycles ->
            cycles.map { cycle ->
                val horizontalPixel = cycle % 40
                val registryValue = signals.last { it.first <= cycle }.second

                if (registryValue in (horizontalPixel - 1..horizontalPixel + 1)) '#' else '.'
            }
        }.joinToString("\n") { it.joinToString("") }
    }
}


fun main() {
    test<Day10>(
        13140, """
        ##..##..##..##..##..##..##..##..##..##..
        ###...###...###...###...###...###...###.
        ####....####....####....####....####....
        #####.....#####.....#####.....#####.....
        ######......######......######......####
        #######.......#######.......#######.....
    """.trimIndent()
    )
    solve<Day10>()
}