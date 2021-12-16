package day13

import java.io.File

data class Point(val x: Int, val y: Int)

enum class FoldAxis {
  HORIZONTAL,
  VERTICAL
}

data class Fold(val axis: FoldAxis, val position: Int)

fun foldX(points: Set<Point>, x: Int): Set<Point> {
  val (left, right) = points.partition { it.x < x }
  return left.union(right.map { point -> point.copy(x = 2 * x - point.x) })
}

fun foldY(points: Set<Point>, y: Int): Set<Point> {
  val (above, below) = points.partition { it.y < y }
  return above.union(below.map { point -> point.copy(y = 2 * y - point.y) })
}

fun applyFold(fold: Fold, points: Set<Point>): Set<Point> {
  return when (fold.axis) {
    FoldAxis.VERTICAL -> foldY(points, fold.position)
    FoldAxis.HORIZONTAL -> foldX(points, fold.position)
  }
}

fun parseInput(lines: List<String>): Pair<Set<Point>, List<Fold>> {
  val emptyLine = lines.indexOf("")
  val points =
      lines
          .subList(0, emptyLine)
          .map { it.split(",", limit = 2) }
          .map { (x, y) -> Point(x.toInt(), y.toInt()) }
          .toSet()
  val folds =
      lines.subList(emptyLine + 1, lines.size).map { it.split("=", limit = 2) }.map { (axis, pos) ->
        Fold(if (axis.endsWith("x")) FoldAxis.HORIZONTAL else FoldAxis.VERTICAL, pos.toInt())
      }
  return points to folds
}

fun solvePart1(lines: List<String>): Int {
  val (points, folds) = parseInput(lines)
  return applyFold(folds.first(), points).size
}

fun paint(points: Set<Point>) {
  val maxX = points.maxOf { it.x }
  val maxY = points.maxOf { it.y }
  for (y in 0..maxY) {
    println(
        (0..maxX).joinToString(separator = "") { x ->
          if (points.contains(Point(x, y))) "#" else " "
        })
  }
}

fun solvePart2(lines: List<String>) {
  val (points, folds) = parseInput(lines)
  paint(folds.fold(points) { p, fold -> applyFold(fold, p) })
}

fun main() {
  val lines = File("src/main/resources", "day13.txt").readLines()
  println("Part1: ${solvePart1(lines)}")
  solvePart2(lines)
}
