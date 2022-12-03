package solutions

import models.InputProvider

val PRIORITIES = ('a'..'z') + ('A'..'Z')

context (InputProvider)
class Day03 : Day(3, 2022, "Rucksack Reorganization") {
    private val rucksacks: List<CharArray> = input.map(String::toCharArray)

    @OptIn(ExperimentalStdlibApi::class)
    override fun part1(): Int {
        val compartments = rucksacks.map { rucksack ->
            val splitIndex = rucksack.size / 2

            // Slice the rucksack into two compartments
            rucksack.slice(0..<splitIndex).toSet() to rucksack.slice(splitIndex..<rucksack.size).toSet()
        }

        return compartments
            .map { (first, second) -> first.first(second::contains) }
            .sumOf { PRIORITIES.indexOf(it) + 1 }
    }

    override fun part2(): Any {
        return rucksacks.map { it.toSet() }.chunked(3)
            .fold(0) { result, group ->
                // The badge is the only common char between the three rucksacks
                val badge = group.reduce { acc, chars -> acc.intersect(chars) }.first()

                // Add the group value to the result
                result + PRIORITIES.indexOf(badge) + 1
            }
    }
}

fun main() {
    test<Day03>(157, 70)
    solve<Day03>()
}