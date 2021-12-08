package day7

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS

@TestInstance(PER_CLASS)
internal class Day07KtTest {
  private val aocExampleInput = """16,1,2,0,4,2,7,1,2,14""".split(",").map { it.toInt() }

  @Test
  fun `test part 1 sample input`() {
    solvePart1(aocExampleInput) shouldBe 37
  }

  @Test
  fun `test part 2 sample input`() {
    solvePart2(aocExampleInput) shouldBe 168
  }
}
