package day01

import helpers.parseInput

fun main() {
    val input = parseInput("/day01.txt")
    val partitionedInput = partitionInput(input)

    val solution1 = part1(partitionedInput)
    println("Solution to part1 is: $solution1")

    val solution2 = part2(partitionedInput)
    println("Solution to part2 is: $solution2")
}


private fun partitionInput(input: List<String>, delimiter: String = ""): List<List<Int>> {
    val splitAt = input.asSequence().withIndex()
        .filter { it.value == delimiter }
        .map { it.index + 1 }
        .plus(listOf(0, input.size - 1)) // add the first and last indices
        .sorted()
        .toList()

    // rune a window over the splitAt-list create sublist from the input
    return splitAt
        .windowed(2, step = 1)
        .map { input.subList(it[0], it[1] - 1) }
        .map { sublist -> sublist.map { it.toInt() } }
}

private fun part1(input: List<List<Int>>): Int {
    return input.maxOf { sublist -> sublist.sum() }
}

private fun part2(input: List<List<Int>>): Int {
    return input.sortedByDescending { sublist -> sublist.sum() }
        .take(3).sumOf { it.sum() }
}