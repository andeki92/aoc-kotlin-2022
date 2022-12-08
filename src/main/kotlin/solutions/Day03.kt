package solutions

import models.InputProvider

context (InputProvider)
class Day03 : Day(3, 2022, "Rucksack Reorganization") {
    private val rucksacks: List<CharArray> = input.map(String::toCharArray)


    override fun part1(): Int {
        val compartments = rucksacks.map { rucksack ->
            val splitIndex = rucksack.size / 2

            // Slice the rucksack into two compartments
            rucksack.slice(0 until splitIndex).toSet() to rucksack.slice(splitIndex until rucksack.size).toSet()
        }

        return compartments
            .map { (first, second) -> first.first(second::contains) }
            .sumOf(Char::toPriority)
    }

    override fun part2(): Any {
        return rucksacks
            .map { it.toSet() }
            .chunked(3)
            .sumOf { group ->
                group.reduce { acc, chars -> acc.intersect(chars) }
                    .first().toPriority()
            }
    }
}

private fun Char.toPriority() = this - (if (isUpperCase()) 'A' - 27 else 'a' - 1)


fun main() {
    test<Day03>(157, 70)
    solve<Day03>()
}