package day6

import java.io.File

fun growFish(fish: List<Int>, days: Int): Long {
  val fishByIncubationDays: LongArray = LongArray(9)
  fish.forEach { fishByIncubationDays[it] += 1L }
  for (i in 1..days) {
    val newFish = fishByIncubationDays[0]
    fishByIncubationDays[7] += newFish
    for (day in 1..8) {
      fishByIncubationDays[day - 1] = fishByIncubationDays[day]
    }
    fishByIncubationDays[8] = newFish
  }
  return fishByIncubationDays.sum()
}

fun solvePart1(fish: List<Int>): Long {
  return growFish(fish, 80)
}

fun solvePart2(fish: List<Int>): Long {
  return growFish(fish, 256)
}

fun main() {
  val input =
      File("src/main/resources", "day06.txt").readLines().get(0).split(",").map { it.toInt() }
  println("Part 1: ${solvePart1(input)}")
  println("Part 2: ${solvePart2(input)}")
}
