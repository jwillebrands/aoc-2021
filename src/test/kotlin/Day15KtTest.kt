package day15

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS

@TestInstance(PER_CLASS)
internal class Day15KtTest {
  private val aocExampleInput =
      """1163751742
1381373672
2136511328
3694931569
7463417111
1319128137
1359912421
3125421639
1293138521
2311944581""".split(
          "\n")

  @Test
  fun `test part 1 sample input`() {
    solvePart1(aocExampleInput) shouldBe 40
  }

  @Test
  fun `test weight translation`() {
    val originalWeight = 8
    translateWeight(originalWeight, 0, 0) shouldBe 8
    translateWeight(originalWeight, 1, 0) shouldBe 9
    translateWeight(originalWeight, 2, 0) shouldBe 1
    translateWeight(originalWeight, 0, 1) shouldBe 9
    translateWeight(originalWeight, 1, 1) shouldBe 1
    translateWeight(originalWeight, 4, 4) shouldBe 7
  }

  @Test
  fun `test part 2 sample input`() {
    solvePart2(aocExampleInput) shouldBe 315
  }
}
