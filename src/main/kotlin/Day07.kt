package day7

import java.io.File
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.min

fun solvePart1(crabPositions: List<Int>): Int {
  val sortedPositions = crabPositions.sorted()
  val median = sortedPositions[sortedPositions.size / 2]
  return sortedPositions.sumOf { abs(it - median) }
}

fun solvePart2(crabPositions: List<Int>): Int {
  val mean: Double = crabPositions.sum().toDouble() / crabPositions.size
  val calculateFuel = { dist: Int -> (dist * (dist + 1)) / 2 }
  return min(
      crabPositions.sumOf { calculateFuel(abs(it - floor(mean).toInt())) },
      crabPositions.sumOf { calculateFuel(abs(it - ceil(mean).toInt())) })
}

fun main() {
  val crabPositions =
      File("src/main/resources", "day07.txt").readLines().flatMap { it.split(",") }.map {
        it.toInt()
      }
  println("Part 1: ${solvePart1(crabPositions)}")
  println("Part 2: ${solvePart2(crabPositions)}")
}
