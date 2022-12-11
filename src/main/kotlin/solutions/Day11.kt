package solutions

import models.InputProvider

data class Monkey(
    val items: MutableList<Long>,
    val operation: (Long) -> Long,
    val testNumber: Long,
    private val trueTarget: Int,
    private val falseTarget: Int,
) {
    var inspectionCount: Long = 0

    fun inspect(item: Long): Long = operation(item).also { inspectionCount++ }
    fun test(item: Long): Int = if (item % testNumber == 0L) trueTarget else falseTarget
}

context (InputProvider)
class Day11 : Day(11, 2022, "Monkey in the Middle") {

    private fun parseMonkey(input: List<String>): Monkey {
        val startingItems =
            input[1].substringAfter("Starting items: ").split(", ").map { it.toLong() }.toMutableList()

        val operation = input[2].substringAfter("old ").split(" ")
            .let { (type, value) -> (type == "+") to value.toLongOrNull() }.let { (isAdd, value) ->
                if (isAdd) { x: Long -> x + (value ?: x) } else { x: Long -> x * (value ?: x) }
            }
        val testNumber = input[3].substringAfter("Test: divisible by ").toLong()
        val trueTarget = input[4].substringAfter("If true: throw to monkey ").toInt()
        val falseTarget = input[5].substringAfter("If false: throw to monkey ").toInt()

        return Monkey(
            items = startingItems,
            operation = operation,
            testNumber = testNumber,
            trueTarget = trueTarget,
            falseTarget = falseTarget,
        )
    }

    private fun solve(monkeys: List<Monkey>, times: Int, worryMitigation: (Long) -> Long): Long {
        repeat(times) { round ->
            monkeys.forEach { monkey ->
                monkey.items.forEach { item ->
                    val nextValue = worryMitigation(monkey.inspect(item))
                    val nextTarget = monkey.test(nextValue)
                    monkeys[nextTarget].items.add(nextValue)
                }
                monkey.items.clear() // remove previous held items
            }

            if ((round + 1) == 1 || (round + 1) == 20) {
                println("")
                println("== After round ${round + 1} ==")
                monkeys.forEachIndexed { index, monkey ->
                    println("Monkey $index inspected items ${monkey.inspectionCount} times.")
                }
            }

            if ((round + 1) % 1000 == 0) {
                println("")
                println("== After round ${round + 1} ==")
                monkeys.forEachIndexed { index, monkey ->
                    println("Monkey $index inspected items ${monkey.inspectionCount} times.")
                }
            }
        }

        return monkeys.sortedBy { it.inspectionCount }.takeLast(2).map(Monkey::inspectionCount).reduce(Long::times)
    }

    override fun part1(): Long {
        val monkeys = input.chunked(7).map(::parseMonkey)
        return solve(monkeys, 20) { it / 3 }
    }

    override fun part2(): Long {
        val monkeys = input.chunked(7).map(::parseMonkey)
        return solve(monkeys, 10_000) {
            it % monkeys.map(Monkey::testNumber).reduce(Long::times)
        }
    }

}


fun main() {
    test<Day11>(10605, 2713310158)
    solve<Day11>()
}