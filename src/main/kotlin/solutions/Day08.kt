package solutions

import models.InputProvider


context (InputProvider)
class Day08 : Day(8, 2022, "Treetop Tree House") {

    private val heightmap = input.map { row ->
        row.map { col -> col.toString().toInt() }
    }

    override fun part1(): Int {
        return heightmap.flatMapIndexed { y, row ->
            row.filterIndexed { x, value ->
                // Perform 4 passes left-to-right, right-to-left, top-to-bottom and bottom-to-top
                val ltr = heightmap[y].take(x)
                val rtl = heightmap[y].drop(x + 1)
                val ttb = heightmap.withIndex().take(y).map { heightmap[it.index][x] }
                val btt = heightmap.withIndex().drop(y + 1).map { heightmap[it.index][x] }

                val passes = listOf(ltr, rtl, ttb, btt)
                passes.any { pass -> pass.all { it < value } }
            }
        }.size
    }

    override fun part2(): Int {
        return heightmap.flatMapIndexed { y, row ->
            row.mapIndexed { x, value ->
                // Perform 4 passes left-to-right, right-to-left, top-to-bottom and bottom-to-top
                val ltr = heightmap[y].take(x).reversed()
                val rtl = heightmap[y].drop(x + 1)
                val ttb = heightmap.withIndex().take(y).map { heightmap[it.index][x] }.reversed()
                val btt = heightmap.withIndex().drop(y + 1).map { heightmap[it.index][x] }

                val scoreList = buildList {
                    add(ltr.getScenicScore(value))
                    add(rtl.getScenicScore(value))
                    add(ttb.getScenicScore(value))
                    add(btt.getScenicScore(value))
                }
                scoreList.reduce { acc, i -> acc * i }
            }
        }.max()
    }
}

fun List<Int>.getScenicScore(treeHeight: Int): Int {
    // if there are any values equal or higher than the current tree, add 1
    return this.takeWhile { it < treeHeight }.size + if (this.any { it >= treeHeight }) 1 else 0
}


fun main() {
    test<Day08>(21, 8)
    solve<Day08>()
}