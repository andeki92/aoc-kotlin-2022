package solutions

import models.InputProvider
import java.lang.Integer.min
import kotlin.math.max

private fun Pair<Int, Int>.flowTo(p: (Pair<Int, Int>) -> Boolean) = sequenceOf(
    copy(second = second + 1),
    copy(first = first - 1, second = second + 1),
    copy(first = first + 1, second = second + 1)
).firstOrNull(p)

private data class Cavern(
    private val rocks: List<Pair<Int, Int>>
) {
    private val origin = 500 to 0
    private var sand: Set<Pair<Int, Int>> = emptySet()

    companion object {
        fun parseCavern(list: List<String>): Cavern {
            val rocks = list.flatMap { line ->
                line.split(" -> ").zipWithNext { from, to ->
                    val (xMin, yMin) = from.split(",").let { it[0].toInt() to it[1].toInt() }
                    val (xMax, yMax) = to.split(",").let { it[0].toInt() to it[1].toInt() }

                    val xMaxActual = max(xMax, xMin)
                    val xMinActual = min(xMax, xMin)

                    val yMaxActual = max(yMax, yMin)
                    val yMinActual = min(yMax, yMin)

                    (xMinActual..xMaxActual).flatMap { x ->
                        (yMinActual..yMaxActual).map { y -> x to y }
                    }
                }
            }.flatten()

            return Cavern(rocks = rocks)
        }
    }

    fun display(margin: Int = 1) {
        val yMax = rocks.maxBy { it.second }.second + margin
        val dx = buildSet {
            add(rocks.maxBy { it.first }.first - 500)
            add(500 - rocks.minBy { it.first }.first)

            // if sand is in the cavern, this might be the xMin/xMax
            if (sand.isNotEmpty()) add(sand.maxBy { it.first }.first - 500)
            if (sand.isNotEmpty()) add(500 - sand.minBy { it.first }.first)
        }.max() + margin

        val xRange = (500 - dx .. 500 + dx)

        (0..yMax).forEach { y ->
            (xRange).joinToString("") { x ->
                if (x to y in rocks) return@joinToString "#"
                if (x to y in sand) return@joinToString "o"
                if (x to y == origin) return@joinToString "+"

                "."
            }.also(::println)
        }.also { (xRange).joinToString("") { "#" }.also(::println) }
    }

    fun fillRocksWithSand(): Int {
        val yMax = rocks.maxBy { it.second }.second

        val stableSand: MutableSet<Pair<Int, Int>> = mutableSetOf()
        var currentSand: Pair<Int, Int> = origin

        while (currentSand.second < yMax + 1) {
            currentSand = currentSand.flowTo { it.second < yMax + 2 && it !in rocks && it !in stableSand }
                ?: origin.also { stableSand.add(currentSand) }
        }

        sand = stableSand.toSet()
        return stableSand.size
    }

    fun fillRoomWithSand(): Int {
        val yMax = rocks.maxBy { it.second }.second

        val stableSand: MutableSet<Pair<Int, Int>> = mutableSetOf()
        var currentSand: Pair<Int, Int> = origin

        while (origin !in stableSand) {
            currentSand = currentSand.flowTo { it.second < yMax + 2 && it !in rocks && it !in stableSand }
                ?: origin.also { stableSand.add(currentSand) }
        }
        sand = stableSand.toSet()
        return stableSand.size
    }

}

context (InputProvider)
class Day14 : Day(14, 2022, "Regolith Reservoir") {

    private val cavern = Cavern.parseCavern(input)

    override fun part1(): Int = cavern.fillRocksWithSand().also { cavern.display() }
    override fun part2(): Int = cavern.fillRoomWithSand().also { cavern.display() }
}


fun main() {
    test<Day14>(24, 93)
    solve<Day14>() // careful - this is slooow!
}