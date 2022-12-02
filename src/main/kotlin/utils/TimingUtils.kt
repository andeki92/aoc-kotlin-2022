package utils

import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue


@OptIn(ExperimentalTime::class)
inline fun runWithTiming(part: String, f: () -> Any?) {
    val (result, duration) = measureTimedValue { f() }
    println("\nSolution $part: (took $duration)\n$result")
}