package day10

import java.io.File

sealed class SyntaxCheckResult(val valid: Boolean) {
  class ValidSyntax : SyntaxCheckResult(true)

  class IncompleteSyntax(val missingTokens: List<Char>) : SyntaxCheckResult(false)

  class CorruptSyntax(val position: Int, val found: Char, val expected: Char?) :
      SyntaxCheckResult(false)
}

class SyntaxChecker {
  private val pairsByOpeningBracket = mapOf('{' to '}', '[' to ']', '<' to '>', '(' to ')')
  private val pairsByClosingBracket =
      pairsByOpeningBracket.map { (opening, closing) -> closing to opening }.toMap()

  fun checkSyntax(line: String): SyntaxCheckResult {
    val opStack = ArrayDeque<Char>()
    for (position in line.indices) {
      val token = line[position]
      when {
        pairsByOpeningBracket[token] != null -> opStack.add(token)
        pairsByClosingBracket[token] != opStack.lastOrNull() ->
            return SyntaxCheckResult.CorruptSyntax(
                position, token, pairsByOpeningBracket[opStack.lastOrNull()])
        pairsByClosingBracket[token] == opStack.last() -> opStack.removeLast()
      }
    }
    return when {
      opStack.isEmpty() -> SyntaxCheckResult.ValidSyntax()
      else ->
          SyntaxCheckResult.IncompleteSyntax(opStack.reversed().map { pairsByOpeningBracket[it]!! })
    }
  }
}

fun solvePart1(lines: List<String>): Int {
  val checker = SyntaxChecker()
  val corruptionWeight = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
  return lines
      .map { checker.checkSyntax(it) }
      .filterIsInstance<SyntaxCheckResult.CorruptSyntax>()
      .sumOf { corruptionWeight[it.found]!! }
}

val missingTokenScore = mapOf(')' to 1, ']' to 2, '}' to 3, '>' to 4)

fun scoreMissingTokens(missingTokens: List<Char>): Long {
  return missingTokens.fold(0) { score, token -> score * 5 + missingTokenScore[token]!! }
}

fun solvePart2(lines: List<String>): Long {
  val checker = SyntaxChecker()
  val scores =
      lines
          .map { checker.checkSyntax(it) }
          .filterIsInstance<SyntaxCheckResult.IncompleteSyntax>()
          .map { scoreMissingTokens(it.missingTokens) }
          .sorted()
  return scores[scores.size / 2]
}

fun main() {
  val lines = File("src/main/resources", "day10.txt").readLines()
  println(solvePart1(lines))
  println(solvePart2(lines))
}
