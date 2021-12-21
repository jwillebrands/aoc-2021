package day15

import common.collections.Grid
import common.geometry.Point
import common.search.dijkstraShortestPath
import java.io.File

data class Vertex<T>(val value: T)

data class Edge<T>(val start: Vertex<T>, val end: Vertex<T>, val weight: Int)

class Graph<T>(private val vertices: Set<Vertex<T>>, edges: Set<Edge<T>>) {
  private val edges = edges.groupBy { it.start }

  private fun connectedVertices(vertex: Vertex<T>): Iterable<Edge<T>> {
    return edges.getOrDefault(vertex, emptyList())
  }

  fun shortestPath(start: Vertex<T>, end: Vertex<T>): List<Vertex<T>> {
    return dijkstraShortestPath(start, end) { vertex ->
      connectedVertices(vertex).map { it.end to it.weight }
    }
  }
}

fun createGraphFromGrid(grid: Grid<Int>): Graph<Point> {
  val vertices = grid.points.map { Vertex(it) }
  return Graph(
      vertices.toSet(),
      vertices
          .flatMap { vertex ->
            grid.neighbouringCells(vertex.value).map { Edge(vertex, Vertex(it), grid[it]) }
          }
          .toSet())
}

fun solvePart1(lines: List<String>): Int {
  val grid = Grid.fromText(lines, { it[0] - '0' })
  val graph = createGraphFromGrid(grid)

  return graph.shortestPath(Vertex(Point(0, 0)), Vertex(Point(grid.width - 1, grid.height - 1)))
      .sumOf { grid[it.value] } - grid[0, 0]
}

fun translateWeight(originalWeight: Int, xOffset: Int, yOffset: Int): Int {
  val newWeight = originalWeight + xOffset + yOffset
  return when {
    newWeight >= 10 -> newWeight - 9
    else -> newWeight
  }
}

fun solvePart2(lines: List<String>): Int {
  val baseDimension = lines.size
  val dimensionRange = (0 until baseDimension * 5)
  val grid = Grid.fromText(lines, { it[0] - '0' })
  val getWeight: (Point) -> Int = { (x, y) ->
    translateWeight(
        grid[x % baseDimension, y % baseDimension], x / baseDimension, y / baseDimension)
  }
  return dijkstraShortestPath(
          Point(0, 0),
          Point(dimensionRange.last, dimensionRange.last),
      ) { p ->
        p.cardinalNeighbours().filter { (x, y) -> x in dimensionRange && y in dimensionRange }.map {
          it to getWeight(it)
        }
      }
      .sumOf(getWeight) - grid[0, 0]
}

fun main() {
  val lines = File("src/main/resources", "day15.txt").readLines()
  println(solvePart1(lines))
  println(solvePart2(lines))
}
