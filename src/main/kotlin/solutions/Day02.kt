package solutions

import models.InputProvider

context (InputProvider)
class Day02 : Day(2, 2022, "Rock Paper Scissors") {
    private val rounds = inputPairs.map { (a, b) -> a.first() to b.first() }

    override fun part1(): Any {
        return rounds.sumOf { (abc, xyz) ->
                val s1 = "XYZ".indexOf(xyz) + 1
                val s2 = when(abc to xyz) {
                    'A' to 'X', 'B' to 'Y', 'C' to 'Z' -> 3 /* Draws */
                    'A' to 'Y', 'B' to 'Z', 'C' to 'X' -> 6 /* Win */
                    'A' to 'Z', 'B' to 'X', 'C' to 'Y' -> 0 /* Loose */
                    else -> error("invalid $abc to $xyz")
                }
                s1 + s2
        }
    }

    override fun part2(): Any {
        return rounds.sumOf { (abc, xyz) ->
            val s1 = when(abc to xyz) {
                'A' to 'Y', 'B' to 'X', 'C' to 'Z' -> 1 /* Rock */
                'A' to 'Z', 'B' to 'Y', 'C' to 'X' -> 2 /* Paper */
                'A' to 'X', 'B' to 'Z', 'C' to 'Y' -> 3 /* Scissor */
                else -> error("invalid $abc to $xyz")
            }

            // X is loose, Y is draw, Z is won
            val s2 = "XYZ".indexOf(xyz) * 3
            s1 + s2
        }
    }
}


fun main() {
    test<Day02>(15, 12)
    solve<Day02>()
}