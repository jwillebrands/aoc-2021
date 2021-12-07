package day5

import java.io.File

data class Point(val x: Int, val y: Int) {
  operator fun plus(vector: Vector2d): Point {
    return Point(x + vector.x, y + vector.y)
  }

  companion object {
    fun fromText(coordinates: String): Point {
      val (x, y) = coordinates.split(",")
      return Point(x.toInt(), y.toInt())
    }
  }
}

data class Vector2d(val x: Int, val y: Int)

data class LineSegment(val start: Point, val end: Point) {
  val isHorizontal
    get() = start.y == end.y

  val isVertical
    get() = start.x == end.x

  fun getDiscretePoints(): Iterable<Point> {
    return when {
      isHorizontal ->
          IntProgression.fromClosedRange(start.x, end.x, end.x.compareTo(start.x)).map {
            Point(it, start.y)
          }
      isVertical ->
          IntProgression.fromClosedRange(start.y, end.y, end.y.compareTo(start.y)).map {
            Point(start.x, it)
          }
      else ->
          throw UnsupportedOperationException("Only horizontal and vertical segments are supported")
    }
  }

  companion object {
    fun fromText(line: String): LineSegment {
      val (p1, p2) = line.split(" -> ")
      return LineSegment(Point.fromText(p1), Point.fromText(p2))
    }
  }
}

fun solvePart1(lines: Sequence<String>): Int {
  return lines
      .map { LineSegment.fromText(it) }
      .filter { it.isHorizontal || it.isVertical }
      .flatMap { it.getDiscretePoints() }
      .groupBy { it }
      .count { it.value.size > 1 }
}

fun solvePart2(lines: Sequence<String>): Int {
  return 0
}

fun main() {
  val part1Result = File("src/main/resources/day05.txt").useLines { solvePart1(it) }
  val part2Result = File("src/main/resources/day05.txt").useLines { solvePart2(it) }
}
