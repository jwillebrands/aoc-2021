import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS

@TestInstance(PER_CLASS)
internal class Day02KtTest {
  private val aocExampleInput = """forward 5
down 5
forward 8
up 3
down 8
forward 2""".split("\n")

  @Test
  fun `test part 1 sample input`() {
    solvePart1(aocExampleInput) shouldBe 150
  }

  @Test
  fun `test part 2 sample input`() {
    solvePart2(aocExampleInput) shouldBe 900
  }
}
