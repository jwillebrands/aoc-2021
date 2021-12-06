package day3

import java.io.File

data class PowerConsumption(val gammaRate: Int, val epsilonRate: Int) {
  fun value(): Long {
    return gammaRate.toLong() * epsilonRate
  }
}

fun vectorAdd(vector1: IntArray, vector2: IntArray): IntArray {
  if (vector1.size != vector2.size) {
    throw IllegalArgumentException("Vectors must be same size")
  }
  val result = IntArray(vector1.size)
  for (i in 0..vector1.size - 1) {
    result[i] = vector1[i] + vector2[i]
  }
  return result
}

fun solvePart1(lines: List<String>): Long {
  val gammaRate =
      lines
          .map { it.map { ch -> if (ch == '0') -1 else 1 }.toIntArray() }
          .fold(IntArray(lines[0].length), ::vectorAdd)
          .map { if (it < 0) 0 else 1 }
          .fold(0, { gammaRate, bit -> (gammaRate shl 1) or bit })
  return PowerConsumption(gammaRate, gammaRate xor "1".repeat(lines[0].length).toInt(2)).value()
}

data class Partitions<T>(val lowest: List<T>, val highest: List<T>) {}

typealias BitCriterion = (Partitions<String>) -> List<String>

fun partitionOnBitFlip(sortedValues: List<String>, bitIndex: Int): Partitions<String> {
  val pivot =
      -(sortedValues.binarySearch {
        when (it.get(bitIndex)) {
          '0' -> -1
          else -> 1
        }
      } + 1)
  return Partitions(sortedValues.subList(0, pivot), sortedValues.subList(pivot, sortedValues.size))
}

fun decodeValue(criterion: BitCriterion, sortedValues: List<String>): Int {
  val wordLength = sortedValues.get(0).length
  var remainingValues = sortedValues
  var bitIndex: Int = 0
  while (remainingValues.size > 1 && bitIndex < wordLength) {
    val partitions = partitionOnBitFlip(remainingValues, bitIndex)
    remainingValues = criterion(partitions)
    bitIndex++
  }
  return remainingValues.get(0).toInt(2)
}

fun solvePart2(lines: List<String>): Long {
  val sortedValues = lines.sorted()
  val oxygenGeneratorRating =
      decodeValue(
          { p -> if (p.highest.size >= p.lowest.size) p.highest else p.lowest }, sortedValues)
  val co2ScrubberRating =
      decodeValue(
          { p -> if (p.lowest.size <= p.highest.size) p.lowest else p.highest }, sortedValues)
  return oxygenGeneratorRating.toLong() * co2ScrubberRating
}

fun main() {
  val input = File("src/main/resources", "day03.txt").readLines()
  println("Part 1: ${solvePart1(input)}")
  println("Part 2: ${solvePart2(input)}")
}
