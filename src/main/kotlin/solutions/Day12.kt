package solutions

import models.InputProvider

private typealias Point = Pair<Int, Int>

private fun Point.neighbours() = sequenceOf(
    first - 1 to second,
    first to second + 1,
    first + 1 to second,
    first to second - 1
)

context (InputProvider)
class Day12 : Day(12, 2022, "Hill Climbing Algorithm") {
    private lateinit var startPoint: Point
    private lateinit var endPoint: Point

    private val route = input.flatMapIndexed { y, line ->
        line.mapIndexed { x, char ->
            val point = y to x
            when (char) {
                'S' -> point to 'a'.also { startPoint = point }
                'E' -> point to 'z'.also { endPoint = point }
                else -> point to char
            }
        }
    }.toMap()

    private val countMap = buildMap {
        var count = 0
        var currentCandidates = setOf(endPoint)
        while (currentCandidates.isNotEmpty()) {
            currentCandidates = buildSet {
                currentCandidates.forEach { candidate ->
                    if (putIfAbsent(candidate, count) != null) return@forEach

                    val value = route.getValue(candidate)
                    candidate.neighbours().forEach { neighbour ->
                        route[neighbour]?.also {
                            if (value - it <= 1) {
                                add(neighbour)
                            }
                        }
                    }
                }
            }
            count++
        }
    }

    override fun part1(): Int = countMap[startPoint]!!

    override fun part2(): Int = route.filterValues { it == 'a' }
        .filterKeys(countMap::containsKey).keys
        .minOf(countMap::getValue)
}


fun main() {
    test<Day12>(31, 29)
    solve<Day12>()
}