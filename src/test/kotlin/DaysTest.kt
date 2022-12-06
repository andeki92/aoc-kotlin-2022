import models.InputProvider
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import solutions.*
import utils.create
import kotlin.test.assertEquals

class DaysTest {

    @TestFactory
    fun `aoc 2022`() = aocTests {
        test<Day01>(24000, 45000)
        test<Day02>(15, 12)
        test<Day03>(157, 70)
        test<Day04>(2, 4)
        test<Day05>("CMZ", "MCD")
        test<Day06>(7, 19)
    }
}

private fun aocTests(builder: AoCTestBuilder.() -> Unit): List<DynamicTest> =
    AoCTestBuilder().apply(builder).build()

private class AoCTestBuilder {

    private val tests = mutableListOf<DynamicTest>()

    inline fun <reified D : Day> test(expectedPart1: Any? = null, expectedPart2: Any? = null) =
        with(InputProvider.Test) {
            tests += listOfNotNull(
                expectedPart1?.let {
                    DynamicTest.dynamicTest("${D::class.simpleName} - Part 1")
                    { assertEquals(expectedPart1.toString(), create<D>().part1().toString()) }
                },
                expectedPart2?.let {
                    DynamicTest.dynamicTest("${D::class.simpleName} - Part 2")
                    { assertEquals(expectedPart2.toString(), create<D>().part2().toString()) }
                })
        }

    fun build(): List<DynamicTest> = tests

}
