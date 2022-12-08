package solutions

import models.InputProvider


context (InputProvider)
class Day08 : Day(8, 2022, "Treetop Tree House") {

    private val heightmap: List<List<Int>> = inputIntMatrix

    override fun part1(): Int {
        return heightmap.flatMapIndexed { y, row ->
            row.filterIndexed { x, height ->
                // Perform 4 passes left-to-right, right-to-left, top-to-bottom and bottom-to-top
                val ltr = heightmap[y].take(x)
                val rtl = heightmap[y].drop(x + 1)
                val ttb = heightmap.withIndex().take(y).map { heightmap[it.index][x] }
                val btt = heightmap.withIndex().drop(y + 1).map { heightmap[it.index][x] }

                val directions = listOf(ltr, rtl, ttb, btt)

                // If any of the passes contain only lower trees
                directions.any { pass -> pass.all { it < height } }
            }
        }.size
    }

    override fun part2(): Int {
        return heightmap.flatMapIndexed { y, row ->
            row.mapIndexed { x, height ->
                // Perform 4 passes left-to-right, right-to-left, top-to-bottom and bottom-to-top
                val ltr = heightmap[y].take(x).reversed()
                val rtl = heightmap[y].drop(x + 1)
                val ttb = heightmap.withIndex().take(y).map { heightmap[it.index][x] }.reversed()
                val btt = heightmap.withIndex().drop(y + 1).map { heightmap[it.index][x] }

                val scoreList = buildList {
                    add(ltr.getScenicScore(height))
                    add(rtl.getScenicScore(height))
                    add(ttb.getScenicScore(height))
                    add(btt.getScenicScore(height))
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