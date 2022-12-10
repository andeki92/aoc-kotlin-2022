package solutions

import models.InputProvider
import kotlin.math.sign
import kotlin.reflect.KFunction1

private data class Position(val x: Int, val y: Int) {
    fun up() = copy(y = this.y + 1)
    fun down() = copy(y = this.y - 1)
    fun right() = copy(x = this.x + 1)
    fun left() = copy(x = this.x - 1)

    fun isTouching(other: Position): Boolean =
        other.x in (this.x - 1..this.x + 1) && other.y in (this.y - 1..this.y + 1)
}

private typealias Step = KFunction1<Position, Position>

context (InputProvider)
class Day09 : Day(9, 2022, "Rope Bridge") {

    private fun getDirection(char: Char): KFunction1<Position, Position> {
        return when (char) {
            'U' -> Position::up
            'D' -> Position::down
            'L' -> Position::left
            'R' -> Position::right
            else -> error("Invalid input: $char")
        }
    }

    private val instructions: List<Step> =
        inputPairs.flatMap { (dir, count) -> (1..count.toInt()).map { getDirection(dir.first()) } }

    override fun part1(): Int = simulateRope(instructions, 2)
    override fun part2(): Int = simulateRope(instructions, 10)
}

private fun moveTail(head: Position, tail: Position): Position =
    if (!head.isTouching(tail)) {
        Position(tail.x + (head.x - tail.x).sign, tail.y + (head.y - tail.y).sign)
    } else tail

private fun simulateRope(instructions: List<Step>, ropeLength: Int = 2): Int {
    val rope = Array(ropeLength) { Position(0, 0) }
    val tailHistory = mutableSetOf(rope.last())

    instructions.forEach { stepFunction ->
        rope[0] = stepFunction(rope[0]) // update head

        // update tail(s)
        rope.indices.drop(1).forEach { i ->
            rope[i] = moveTail(rope[i - 1], rope[i])
        }

        tailHistory += rope.last()
    }
    return tailHistory.size
}

fun main() {
    test<Day09>(13, 1)
    solve<Day09>()
}
