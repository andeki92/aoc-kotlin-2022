package solutions

import models.InputProvider

val digitMatcher = "\\d+".toRegex()

private typealias Stack = ArrayDeque<Char>

private fun Stack.push(value: Char) = add(value)
private fun Stack.push(values: List<Char>) = addAll(values)
private fun Stack.pop() = removeLast()
private fun Stack.pop(count: Int) = takeLast(count).also { repeat(count) { removeLast() } }

private typealias Stacks = List<Stack>

private fun Stacks.topCrates() = map { it.last() }.joinToString("")
private fun Stacks.copy() = map { ArrayDeque(it) }


context (InputProvider)
class Day05 : Day(5, 2022, "Supply Stacks") {

    private val rawInput = input.let {
        val instructionIndex = input.indexOfFirst(String::isEmpty)
        it.subList(0, instructionIndex) to it.subList(instructionIndex + 1, it.size)
    }

    private val initialStacks: Stacks = rawInput.first.dropLast(1).let { stacksInput ->
        val buckets = (stacksInput.maxBy { it.length }.length + 1) / 4
        val stacks = (1..buckets).map { Stack() }

        stacksInput.forEach {
            it.chunked(4).forEachIndexed { index, crate ->
                if (crate.contains("[")) stacks[index].addFirst(crate[1])
            }
        }

        stacks
    }

    private val instructions = rawInput.second.map { instruction ->
        digitMatcher.findAll(instruction).map(MatchResult::value).toList()
    }.map { Triple(it[0].toInt(), it[1].toInt() - 1, it[2].toInt() - 1) }


    override fun part1(): String {
        return instructions.fold(initialStacks.copy()) { stacks, (count, from, to) ->
            repeat(count) {
                stacks[to].push(stacks[from].pop())
            }
            stacks
        }.topCrates()
    }

    override fun part2(): String {
        return instructions.fold(initialStacks.copy()) { stacks, (count, from, to) ->
            stacks[to].push(stacks[from].pop(count))
            stacks
        }.topCrates()
    }
}


fun main() {
    test<Day05>("CMZ", "MCD")
    solve<Day05>()
}