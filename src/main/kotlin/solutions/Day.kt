package solutions

import models.InputProvider
import utils.chunked
import utils.create
import utils.runWithTiming

context(InputProvider)
sealed class Day(val day: Int, private val year: Int = 2022, val title: String) {

    val input: List<String> by lazy { loadInput(day) }

    /**
     * Input helper methods making bootstrapping easier
     */
    val inputPairs: List<Pair<String, String>> by lazy {
        input.map { round -> round.split(" ").let { it[0] to it[1] } }
    }
    val inputChunks: List<List<String>> by lazy { input.chunked(String::isEmpty) }
    val inputIntMatrix: List<List<Int>> by lazy {
        input.map { row -> row.map { it.toString().toInt() } }
    }

    init {
        require(day in 1..25) { "Invalid day $day" }
        require(year in 2022..2022) { "Invalid year $year" }
        require(input.isNotEmpty()) { "Empty input" }
    }

    abstract fun part1(): Any
    abstract fun part2(): Any

    fun solve() {
        runWithTiming("1") { part1() }
        runWithTiming("2") { part2() }
    }
}

inline fun <reified T : Day> solve() {
    with(InputProvider.Competition) {
        create<T>().solve()
    }
}

inline fun <reified T : Day> test(expectedPart1: Any?, expectedPart2: Any?) {
    with(InputProvider.Test) {
        with(create<T>()) {
            println("Checking part 1... ")
            part1().let { result ->
                check(result.toString() == expectedPart1.toString()) {
                    "Part 1 result failed!\nExpected: $expectedPart1\nActual: $result"
                }
            }
            println("Checking part 2... ")
            part2().let { result ->
                check(result.toString() == expectedPart2.toString()) {
                    "Part 2 result failed!\nExpected: $expectedPart2\nActual: $result"
                }
            }
        }
    }
}


