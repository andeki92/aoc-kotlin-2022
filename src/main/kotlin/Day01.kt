context (InputProvider)
class Day01 : Day(1, 2022) {
    private val calorieList = inputChunks.map { c -> c.map(String::toInt) }

    override fun part1(): Any {
        return calorieList.maxBy { it.sum() }.sum()
    }

    override fun part2(): Any {
        return calorieList.sortedByDescending { it.sum() }.take(3).sumOf { it.sum() }
    }
}

fun main() {
    test<Day01>(24000, 45000)
    solve<Day01>()
}