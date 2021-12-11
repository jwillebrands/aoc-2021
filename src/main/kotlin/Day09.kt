package day9

import java.io.File

data class Point(val x: Int, val y: Int)

data class HeightMap(private val data: List<List<Int>>) {
  private val height = data.size
  private val width = data[0].size

  operator fun get(p: Point): Int {
    return data[p.y][p.x]
  }

  private fun neighbouringCells(p: Point): List<Point> {
    val (x, y) = p
    return listOf(Point(x, y - 1), Point(x, y + 1), Point(x - 1, y), Point(x + 1, y)).filterNot {
      it.x < 0 || it.x >= width || it.y < 0 || it.y >= height
    }
  }
  private fun isLowest(cell: Int, neighbouringCells: List<Int>): Boolean {
    return neighbouringCells.all { cell < it }
  }

  fun lowPoints(): List<Point> {
    return points.filter {
      isLowest(get(it), neighbouringCells(it).map { neighbour -> get(neighbour) })
    }
  }

  fun getBasin(lowPoint: Point): List<Point> {
    val basin = mutableListOf<Point>()
    val visited = mutableMapOf<Point, Boolean>()
    val pointsToExplore = ArrayDeque<Point>()
    pointsToExplore.add(lowPoint)
    while (pointsToExplore.isNotEmpty()) {
      val point = pointsToExplore.removeFirst()
      if (point !in visited) {
        visited[point] = true
        basin.add(point)
        pointsToExplore.addAll(
            neighbouringCells(point).filter { get(it) > get(point) && get(it) < 9 })
      }
    }
    return basin
  }

  private val points
    get() = (data.indices).flatMap { y -> data[y].indices.map { x -> Point(x, y) } }
}

fun solvePart1(lines: List<String>): Int {
  val heightMap = HeightMap(lines.map { it.map { ch -> ch - '0' } })
  return heightMap.lowPoints().sumOf { heightMap[it] + 1 }
}

fun solvePart2(lines: List<String>): Int {
  val heightMap = HeightMap(lines.map { it.map { ch -> ch - '0' } })
  val basinsBySizeDescending =
      heightMap.lowPoints().map { heightMap.getBasin(it) }.sortedByDescending { it.size }
  return basinsBySizeDescending.take(3).fold(1) { acc, basin -> acc * basin.size }
}

fun main() {
  val lines = File("src/main/resources", "day09.txt").readLines()
  println(solvePart1(lines))
  println(solvePart2(lines))
}
