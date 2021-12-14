package day11

import java.io.File

class DumboOctopus(var powerLevel: Int) {
  fun reachedCriticalMass(): Boolean {
    return powerLevel == 10
  }

  fun flash() {
    powerLevel = 0
  }

  fun incrementPowerLevel() {
    powerLevel++
  }

  fun hasFlashed(): Boolean {
    return powerLevel == 0
  }
}

data class Point(val x: Int, val y: Int)

class Grid<T>(private val data: List<List<T>>) {
  private val height = data.size
  private val width = data[0].size

  operator fun get(p: Point): T {
    return data[p.y][p.x]
  }

  private fun diagonalNeighbours(p: Point): List<Point> {
    val (x, y) = p
    return listOf(
        Point(x - 1, y - 1), Point(x + 1, y - 1), Point(x - 1, y + 1), Point(x + 1, y + 1))
  }

  private fun cardinalNeighbours(p: Point): List<Point> {
    val (x, y) = p
    return listOf(Point(x, y - 1), Point(x, y + 1), Point(x - 1, y), Point(x + 1, y))
  }

  fun neighbouringCells(p: Point, includeDiagonals: Boolean = false): List<Point> {
    return when {
      includeDiagonals -> diagonalNeighbours(p) + cardinalNeighbours(p)
      else -> cardinalNeighbours(p)
    }.filterNot { it.x < 0 || it.x >= width || it.y < 0 || it.y >= height }
  }

  val points
    get() = (0 until height).flatMap { y -> (0 until width).map { x -> Point(x, y) } }

  val values
    get() = points.map { this[it] }

  val entries
    get() = points.map { it to this[it] }
}

class OctopusCave(grid: List<List<DumboOctopus>>) {
  private val grid = Grid(grid)

  private fun handleFlashTargets(p: Point): List<Point> {
    return grid.neighbouringCells(p, true)
        .asSequence()
        .map { it to grid[it] }
        .filterNot { (_, octo) -> octo.hasFlashed() }
        .onEach { (_, octo) -> octo.incrementPowerLevel() }
        .filter { (_, octo) -> octo.reachedCriticalMass() }
        .map { (p) -> p }
        .toList()
  }

  fun simulateStep(): Int {
    val flashes = mutableListOf<Point>()
    for ((point, octopus) in grid.entries) {
      octopus.incrementPowerLevel()
      if (octopus.reachedCriticalMass()) {
        flashes.add(point)
      }
    }
    var i = 0
    while (i < flashes.size) {
      val point = flashes[i]
      val octopus = grid[point]
      octopus.flash()
      flashes.addAll(handleFlashTargets(point))
      i++
    }
    return i
  }
}

fun solvePart1(lines: List<String>): Int {
  val octos = lines.map { it.map { ch -> DumboOctopus(ch - '0') } }
  val cave = OctopusCave(octos)
  return (1..100).sumOf { cave.simulateStep() }
}

fun solvePart2(lines: List<String>): Int {
  val octos = lines.map { it.map { ch -> DumboOctopus(ch - '0') } }
  val cave = OctopusCave(octos)
  var step = 1
  while (cave.simulateStep() != 100) {
    step++
  }
  return step
}

fun main() {
  val lines = File("src/main/resources", "day11.txt").readLines()
  println("Part1: ${solvePart1(lines)}")
  println("Part2: ${solvePart2(lines)}")
}
