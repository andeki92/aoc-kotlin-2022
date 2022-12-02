import utils.chunked
import utils.runWithTiming
import kotlin.io.path.Path
import kotlin.io.path.readLines


sealed interface InputProvider {
    fun loadInput(day: Int): List<String>

    companion object {
        private fun getBaseFilename(day: Int): String = "day${"%02d".format(day)}"
        private fun getFile(filename: String): List<String> = Path("src", "main", "resources", filename).readLines()
    }


    object Test : InputProvider {
        override fun loadInput(day: Int): List<String> = getFile("${getBaseFilename(day)}.example.txt")
    }

    object Competition : InputProvider {
        override fun loadInput(day: Int): List<String> = getFile("${getBaseFilename(day)}.txt")
    }
}


inline fun <reified T : Day> solve() {
    with(InputProvider.Competition) {
        create<T>().solve()
    }
}

inline fun <reified T : Day> test(expectedPart1: Any?, expectedPart2: Any?) {
    with(InputProvider.Test) {
        with(create<T>()) {
            println("Checking part 1... ")
            part1().let { result ->
                check(result.toString() == expectedPart1.toString()) {
                    "Part 1 result failed!\nExpected: $expectedPart1\nActual: $result"
                }
            }
            println("Checking part 2... ")
            part2().let { result ->
                check(result.toString() == expectedPart2.toString()) {
                    "Part 2 result failed!\nExpected: $expectedPart2\nActual: $result"
                }
            }
        }
    }
}

context (InputProvider)
inline fun <reified T : Day> create(): T {
    return T::class.constructors.first { it.parameters.isEmpty() }.call(this@InputProvider)
}

context(InputProvider)
sealed class Day(private val day: Int, private val year: Int = 2022) {

    private val input: List<String> by lazy { loadInput(day) }

    /**
     * Input helper methods making bootstrapping easier
     */
    val inputPairs: List<Pair<String, String>> by lazy {
        input.map { round -> round.split(" ").let { it[0] to it[1] } }
    }
    val inputChunks: List<List<String>> by lazy { input.chunked(String::isEmpty) }

    init {
        require(day in 1..25) { "Invalid day $day" }
        require(year in 2022..2022) { "Invalid year $year" }
        require(input.isNotEmpty()) { "Empty input" }
    }

    abstract fun part1(): Any
    abstract fun part2(): Any

    fun solve() {
        runWithTiming("1") { part1() }
        runWithTiming("2") { part2() }
    }
}
