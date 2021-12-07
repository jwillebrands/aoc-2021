package day6

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS

@TestInstance(PER_CLASS)
internal class Day06KtTest {
  private val aocExampleInput = """3,4,3,1,2""".split(",").map { it.toInt() }

  @Test
  fun `test part 1 sample input`() {
    solvePart1(aocExampleInput) shouldBe 5934
  }

  @Test
  fun `test part 2 sample input`() {
    solvePart2(aocExampleInput) shouldBe 26984457539
  }
}
