package day6

import java.io.File

fun solvePart1(fish: List<Int>): Int {
  val fishByIncubationDays: IntArray = IntArray(9)
  fish.forEach { fishByIncubationDays[it] += 1 }
  for (i in 1..80) {
    val newFish = fishByIncubationDays[0]
    fishByIncubationDays[7] += newFish
    for (day in 1..8) {
      fishByIncubationDays[day - 1] = fishByIncubationDays[day]
    }
    fishByIncubationDays[8] = newFish
  }
  return fishByIncubationDays.sum()
}

fun solvePart2(fish: List<Int>): Int {
  return 0
}

fun main() {
  val input =
      File("src/main/resources", "day06.txt").readLines().get(0).split(",").map { it.toInt() }
  println("Part 1: ${solvePart1(input)}")
  println("Part 2: ${solvePart2(input)}")
}
