package solutions

import models.InputProvider


context (InputProvider)
class Day08 : Day(8, 2022, "Treetop Tree House") {

    private val heightmap: List<List<Int>> = inputIntMatrix

    override fun part1(): Int {
        return heightmap.flatMapIndexed { y, row ->
            row.filterIndexed { x, height ->
                val column = heightmap.map { it[x] }

                // Perform 4 passes left-to-right, right-to-left, top-to-bottom and bottom-to-top
                val ltr = row.take(x)
                val rtl = row.drop(x + 1)
                val ttb = column.take(y)
                val btt = column.drop(y + 1)

                val directions = listOf(ltr, rtl, ttb, btt)

                // If any of the passes contain only lower trees
                directions.any { pass -> pass.all { it < height } }
            }
        }.size
    }

    override fun part2(): Int {
        return heightmap.flatMapIndexed { y, row ->
            row.mapIndexed { x, height ->
                val column = heightmap.map { it[x] }

                // Perform 4 passes left-to-right, right-to-left, top-to-bottom and bottom-to-top
                val ltr = row.take(x).reversed() // here x, y is the 'origin'
                val rtl = row.drop(x + 1)
                val ttb = column.take(y).reversed()
                val btt = column.drop(y + 1)

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