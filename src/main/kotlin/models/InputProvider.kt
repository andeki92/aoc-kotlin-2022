package models

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
