package day8

import java.io.File

data class OverlapFingerprint(val overlapCount: List<Int>)

class SegmentPattern(segments: String) {
  private val segments = segments.toSet()
  val segmentCount
    get() = segments.size

  fun overlapCount(other: SegmentPattern): Int {
    return segments.count { other.segments.contains(it) }
  }

  fun determineOverlap(referenceSet: List<SegmentPattern>): OverlapFingerprint {
    return OverlapFingerprint(referenceSet.map { overlapCount(it) })
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as SegmentPattern

    if (segments != other.segments) return false

    return true
  }

  override fun hashCode(): Int {
    return segments.hashCode()
  }
}

enum class SevenSegmentDigit(val pattern: SegmentPattern) {
  ZERO(SegmentPattern("abcefg")),
  ONE(SegmentPattern("cf")),
  TWO(SegmentPattern("acdeg")),
  THREE(SegmentPattern("acdfg")),
  FOUR(SegmentPattern("bcdf")),
  FIVE(SegmentPattern("abdfg")),
  SIX(SegmentPattern("abdefg")),
  SEVEN(SegmentPattern("acf")),
  EIGHT(SegmentPattern("abcdefg")),
  NINE(SegmentPattern("abcdfg"));

  val digit
    get() = ordinal

  companion object {
    val uniqueDigitsBySegmentCount: Map<Int, SevenSegmentDigit> =
        values().groupBy { it.pattern.segmentCount }.filterValues { it.size == 1 }.mapValues {
          it.value[0]
        }

    val nonUniqueSegmentCountDigits =
        values().filterNot { uniqueDigitsBySegmentCount.containsValue(it) }
  }
}

data class NotebookEntry(val uniquePatterns: List<String>, val outputValue: List<String>)

fun parseLine(line: String): NotebookEntry {
  val (patterns, output) = line.split("|")
  return NotebookEntry(patterns.trim().split(" "), output.trim().split(" "))
}

fun solvePart1(input: Sequence<String>): Int {
  return input.map(::parseLine).flatMap { it.outputValue }.count {
    SevenSegmentDigit.uniqueDigitsBySegmentCount.containsKey(it.length)
  }
}

class UniqueOverlapClassifier(private val trainingSet: List<String>) {
  private val referenceDigitsByOverlap: Map<OverlapFingerprint, SevenSegmentDigit> =
      SevenSegmentDigit.nonUniqueSegmentCountDigits.associateBy {
        it.pattern.determineOverlap(
            SevenSegmentDigit.uniqueDigitsBySegmentCount.values.map { digit -> digit.pattern })
      }

  private val overlapPatternBySegment: Map<SegmentPattern, OverlapFingerprint> by lazy {
    val (unique, nonUnique) =
        trainingSet.map { SegmentPattern(it) }.partition {
          SevenSegmentDigit.uniqueDigitsBySegmentCount.containsKey(it.segmentCount)
        }
    val uniqueDigitsToPattern =
        unique
            .associateBy { SevenSegmentDigit.uniqueDigitsBySegmentCount[it.segmentCount]!! }
            .toSortedMap()
    nonUnique.associateWith { it.determineOverlap(uniqueDigitsToPattern.values.toList()) }
  }

  fun classify(segments: String): SevenSegmentDigit? {
    val uniqueDigit = SevenSegmentDigit.uniqueDigitsBySegmentCount[segments.length]
    return uniqueDigit ?: deduceClassification(segments)
  }

  private fun deduceClassification(segments: String): SevenSegmentDigit? {
    val overlapFingerprint = overlapPatternBySegment[SegmentPattern(segments)]
    return referenceDigitsByOverlap[overlapFingerprint]
  }
}

fun solvePart2(input: Sequence<String>): Int {
  return input
      .map { parseLine(it) }
      .map { it.outputValue.map(UniqueOverlapClassifier(it.uniquePatterns)::classify) }
      .map { digits -> digits.fold(0) { acc, digit -> acc * 10 + digit!!.digit } }
      .sum()
}

fun main() {
  val input = File("src/main/resources", "day08.txt")
  input.useLines { println("Part 1: ${solvePart1(it)}") }
  input.useLines { println("Part 2: ${solvePart2(it)}") }
}
