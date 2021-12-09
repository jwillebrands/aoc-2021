package day8

import java.io.File

enum class SevenSegmentDigit(val segments: String) {
  ZERO("abcefg"),
  ONE("cf"),
  TWO("acdeg"),
  THREE("acdfg"),
  FOUR("bcdf"),
  FIVE("abdfg"),
  SIX("abdefg"),
  SEVEN("acf"),
  EIGHT("abcdefg"),
  NINE("abcdfg");

  val segmentCount
    get() = segments.length

  val digit
    get() = this.ordinal

  fun overlapCount(other: SevenSegmentDigit): Int {
    return segments.count { other.segments.contains(it) }
  }
}

interface SevenDigitClassifier {
  fun classify(segments: String): SevenSegmentDigit?
}

class UniqueCountClassifier : SevenDigitClassifier {
  val uniqueDigitsBySegmentCount =
      SevenSegmentDigit.values()
          .groupBy { it.segmentCount }
          .filterValues { it.size == 1 }
          .mapValues { it.value[0] }

  override fun classify(segments: String): SevenSegmentDigit? {
    return uniqueDigitsBySegmentCount.get(segments.length)
  }
}

data class NotebookEntry(val uniquePatterns: List<String>, val outputValue: List<String>)

fun parseLine(line: String): NotebookEntry {
  val (patterns, output) = line.split("|")
  return NotebookEntry(patterns.trim().split(" "), output.trim().split(" "))
}

fun solvePart1(input: Sequence<String>): Int {
  val uniqueClassifier = UniqueCountClassifier()
  return input.map(::parseLine).flatMap { it.outputValue }.count {
    uniqueClassifier.classify(it) != null
  }
}


fun solvePart2(input: Sequence<String>): Int {
  return 0
}

fun main() {
  File("src/main/resources", "day08.txt").useLines {
    println("Part 1: ${solvePart1(it)}")
    println("Part 2: ${solvePart2(it)}")
  }
}
