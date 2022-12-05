package solutions

import models.InputProvider


val digitMatcher = "\\d+".toRegex()

context (InputProvider)
class Day05 : Day(5, 2022, "Supply Stacks") {

    private val rawInput = input.let {
        val instructionIndex = input.indexOfFirst(String::isEmpty)
        it.subList(0, instructionIndex) to it.subList(instructionIndex + 1, it.size)
    }

    private val arrangement = rawInput.first.dropLast(1).let { stacksInput ->
        val stacks = stacksInput.map { input -> input.chunked(4).map { it[1] } }
        val maxStack = stacks.maxBy { it.size }.size

        (0 until maxStack).map { row ->
            (stacks.indices).mapNotNull { col ->
                stacks.getOrNull(col)?.getOrNull(row)
            }.filterNot(Char::isWhitespace).reversed()
        }
    }

    private val instructions = rawInput.second.map { instruction ->
        digitMatcher.findAll(instruction).map(MatchResult::value).toList()
    }.map { Triple(it[0].toInt(), it[1].toInt(), it[2].toInt()) }


    override fun part1(): Any {
        return instructions.fold(arrangement.toMutableList()) { acc, (count, from, to) ->
            repeat(count) {
                acc[to - 1] = acc[to - 1].plus(acc[from - 1].last())
                acc[from - 1] = acc[from - 1].dropLast(1)
            }
            acc
        }.joinToString("") { it.last().toString() }
    }

    override fun part2(): Any {
        return instructions.fold(arrangement.toMutableList()) { acc, (count, from, to) ->
            acc[to - 1] = acc[to - 1].plus(acc[from - 1].takeLast(count))
            acc[from - 1] = acc[from - 1].dropLast(count)
            acc
        }.joinToString("") { it.last().toString() }
    }
}


fun main() {
    test<Day05>("CMZ", "MCD")
    solve<Day05>()
}