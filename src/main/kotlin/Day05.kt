package day5

import java.io.File
import kotlin.math.max
import kotlin.math.min

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

data class Vector2d(val x: Int, val y: Int) {
  operator fun times(factor: Int): Vector2d {
    return Vector2d(x * factor, y * factor)
  }
}

data class LineSegment(val start: Point, val end: Point) {
  val isHorizontal
    get() = start.y == end.y

  val isVertical
    get() = start.x == end.x

  val direction
    get() = Vector2d(end.x.compareTo(start.x), end.y.compareTo(start.y))

  private val directionMultiplier
    get(): Int =
        max(max(start.x, end.x) - min(start.x, end.x), max(start.y, end.y) - min(start.y, end.y))

  fun getDiscretePoints(): Iterable<Point> {
    return (0..directionMultiplier).map { start + direction * it }
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
  return lines
      .map { LineSegment.fromText(it) }
      .flatMap { it.getDiscretePoints() }
      .groupBy { it }
      .count { it.value.size > 1 }
}

fun main() {
  val part1Result = File("src/main/resources/day05.txt").useLines { solvePart1(it) }
  val part2Result = File("src/main/resources/day05.txt").useLines { solvePart2(it) }
  println("Part 1 ${part1Result}")
  println("Part 2 ${part2Result}")
}
