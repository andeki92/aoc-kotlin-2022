package solutions

import models.InputContext

context (InputContext)
class Day04 : Day(4, 2022, "Camp Cleanup") {
    private val assignments: List<Pair<IntRange, IntRange>> = input.map { line ->
        line.split(",").let {
            it[0].toRange() to it[1].toRange()
        }
    }


    override fun part1(): Any {
        return assignments.count { (first, second) ->
            first.fullyContains(second) || second.fullyContains(first)
        }
    }

    override fun part2(): Any {
        return assignments.count { (first, second) ->
            first.overlaps(second)
        }
    }
}

fun String.toRange(): IntRange =
    this.split("-").let { IntRange(it[0].toInt(), it[1].toInt()) }

fun IntRange.fullyContains(other: IntRange): Boolean =
    this.first <= other.first && this.last >= other.last

fun IntRange.overlaps(other: IntRange): Boolean =
    this.any { it in other }

fun main() {
    test<Day04>(2, 4)
    solve<Day04>()
}