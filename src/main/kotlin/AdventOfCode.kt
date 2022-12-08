import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.terminal.Terminal
import models.InputContext
import solutions.Day
import utils.create
import kotlin.reflect.KClass
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue


@ExperimentalTime
fun main() {
    val t = Terminal()
    t.println(TextColors.red("\n~~~ Advent Of Code Runner ~~~\n"))
    val dayClasses = getAllDayClasses().sortedBy { it.simpleName }

    with(InputContext.Competition) {
        val totalDuration = dayClasses.map { create(it).execute() }.reduceOrNull(Duration::plus)
        println("\nTotal runtime: $totalDuration")
    }
}

private fun getAllDayClasses(): Set<KClass<out Day>> =
    Day::class.sealedSubclasses.toSet()

@ExperimentalTime
private fun <T : Day> T.execute(): Duration {
    print("${day}: ${this.title}".paddedTo(30, 30))
    val part1 = measureTimedValue { this.part1() }
    println("Part 1 [${part1.duration.toString().padStart(6)}]: ${part1.value}")
    print(" ".repeat(30))
    val part2 = measureTimedValue { this.part2() }
    println("Part 2 [${part2.duration.toString().padStart(6)}]: ${part2.value}")
    return part1.duration + part2.duration
}

fun String.paddedTo(minWidth: Int, maxWidth: Int) =
    when {
        length > maxWidth -> this.substring(0, maxWidth - 3) + "..."
        length < minWidth -> this.padEnd(minWidth)
        else -> this
    }




