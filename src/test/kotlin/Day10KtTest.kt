package day10

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS

@TestInstance(PER_CLASS)
internal class Day10KtTest {
  private val aocExampleInput =
      """[({(<(())[]>[[{[]{<()<>>
[(()[<>])]({[<{<<[]>>(
{([(<{}[<>[]}>{[]{[(<()>
(((({<>}<{<{<>}{[]{[]{}
[[<[([]))<([[{}[[()]]]
[{[{({}]{}}([{[{{{}}([]
{<[[]]>}<{[{[{[]{()[[[]
[<(<(<(<{}))><([]([]()
<{([([[(<>()){}]>(<<{{
<{([{{}}[<[[[<>{}]]]>[]]""".split(
          "\n")

  @Test
  fun `test part 1 sample input`() {
    solvePart1(aocExampleInput) shouldBe 26397
  }

  @Test
  fun `test part 2 sample input`() {
    solvePart2(aocExampleInput) shouldBe 288957
  }
}
