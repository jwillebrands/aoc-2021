import org.junit.jupiter.params.ParameterizedTest
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(PER_CLASS)
internal class Day01KtTest {
    private val aocExampleInput = listOf(199, 200, 208, 210, 200, 207, 240, 269, 260, 263)

    @ParameterizedTest
    @MethodSource("part1TestCases")
    fun `test increasing measurement count`(data: TestData) {
        countIncreasingMeasurements(data.input) shouldBe data.expectedCount
    }

    @ParameterizedTest
    @MethodSource("part2TestCases")
    fun `test increasing measurement count with sliding window`(data:TestData) {
        countIncreasingMeasurementsWindowed(data.input) shouldBe data.expectedCount
    }


    private fun part1TestCases(): Stream<TestData> {
        return Stream.of(
            TestData(aocExampleInput, 7),
            TestData(emptyList(), 0),
            TestData(listOf(4,2,1), 0)
        )
    }

    private fun part2TestCases(): Stream<TestData> {
        return Stream.of(
            TestData(aocExampleInput, 5)
        )
    }

    data class TestData(val input: List<Int>, val expectedCount: Int)
}