package day14

import java.io.File

data class PolymerizationInput(val template: String, val rules: Map<String, String>)

fun parseInput(lines: List<String>): PolymerizationInput {
  return PolymerizationInput(
      lines.first(),
      lines.subList(2, lines.size).map { it.split(" -> ") }.associate { it[0] to it[1] })
}

fun simulatePolymerization(start: String, insertionRules: Map<String, String>) =
    generateSequence(start) { template ->
      template
          .windowed(2)
          .map { insertionRules[it]!! }
          .plus(template.last())
          .joinToString(separator = "")
    }

fun solvePart1(lines: List<String>): Int {
  val input = parseInput(lines)
  val rules = input.rules.mapValues { (key, insertion) -> key[0] + insertion }
  val polymer = simulatePolymerization(input.template, rules).elementAt(10)
  val charsByOccurrenceAsc = polymer.groupingBy { it }.eachCount().entries.sortedBy { it.value }
  return charsByOccurrenceAsc.last().value - charsByOccurrenceAsc.first().value
}

val memoizedPolymerizations = mutableMapOf<String, List<String>>()

fun polymerizePair(pair: String, rules: Map<String, String>): List<String> {
  return memoizedPolymerizations.computeIfAbsent(pair) {
    listOf(pair[0] + rules[pair]!!, rules[pair] + pair[1])
  }
}

fun polymerize(template: String, insertionRules: Map<String, String>) =
    generateSequence(
        template.windowed(2).groupingBy { it }.eachCount().mapValues { it.value.toLong() }) { it ->
      it.entries.fold(mutableMapOf()) { acc, (pair, count) ->
        acc.apply {
          polymerizePair(pair, insertionRules).forEach {
            this[it] = this.getOrDefault(it, 0) + count
          }
        }
      }
    }

fun solvePart2(lines: List<String>): Long {
  val (template, rules) = parseInput(lines)
  val polymerPairs: Map<String, Long> = polymerize(template, rules).elementAt(40)
  val charsByOccurrenceAsc =
      polymerPairs.entries
          .fold(mutableMapOf(template.last() to 1L)) { histogram, (pair, count) ->
            histogram.apply { set(pair[0], getOrDefault(pair[0], 0) + count) }
          }
          .entries
          .sortedBy { it.value }
  return charsByOccurrenceAsc.last().value - charsByOccurrenceAsc.first().value
}

fun main() {
  val lines = File("src/main/resources", "day14.txt").readLines()
  println(solvePart1(lines))
  println(solvePart2(lines))
}
