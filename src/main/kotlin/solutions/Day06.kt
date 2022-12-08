package solutions

import models.InputContext

context (InputContext)
class Day06 : Day(6, 2022, "Tuning Trouble") {

    private val signal = input.first()

    override fun part1(): Int = signal.indexOfFirstUniqueWindow(4)
    override fun part2(): Int = signal.indexOfFirstUniqueWindow(14)

    private fun String.indexOfFirstUniqueWindow(window: Int): Int =
        this.windowed(window).indexOfFirst { it.toSet().size == window } + window
}


fun main() {
    test<Day06>(7, 19)
    solve<Day06>()
}