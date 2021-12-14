package day12

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(PER_CLASS)
internal class Day12KtTest {
    @ParameterizedTest
    @MethodSource("testData")
    fun `test part 1 sample input`(testData: TestData) {
        solvePart1(testData.lines) shouldBe testData.part1Expectation
    }

    @ParameterizedTest
    @MethodSource("testData")
    fun `test part 2 sample input`(testData: TestData) {
        solvePart2(testData.lines) shouldBe testData.part2Expectation
    }

    fun testData(): Stream<TestData> {
        return Stream.of(
            TestData(
                """
                start-A
                start-b
                A-c
                A-b
                b-d
                A-end
                b-end
                """.trimIndent().split("\n"), 10, 36
            ),
            TestData(
                """
                dc-end
                HN-start
                start-kj
                dc-start
                dc-HN
                LN-dc
                HN-end
                kj-sa
                kj-HN
                kj-dc
                """.trimIndent().split("\n"), 19, 103
            ),
            TestData(
                """
                fs-end
                he-DX
                fs-he
                start-DX
                pj-DX
                end-zg
                zg-sl
                zg-pj
                pj-he
                RW-he
                fs-DX
                pj-RW
                zg-RW
                start-pj
                he-WI
                zg-he
                pj-fs
                start-RW
                """.trimIndent().split("\n"), 226, 3509
            )
        )
    }

    data class TestData(val lines: List<String>, val part1Expectation: Int, val part2Expectation: Int)
}
