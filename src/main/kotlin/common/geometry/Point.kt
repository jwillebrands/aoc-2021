package common.geometry

data class Point(val x: Int, val y: Int) {
  fun diagonalNeighbours(): List<Point> {
    return listOf(
        Point(x - 1, y - 1), Point(x + 1, y - 1), Point(x - 1, y + 1), Point(x + 1, y + 1))
  }

  fun cardinalNeighbours(): List<Point> {
    return listOf(copy(y = y - 1), copy(y = y + 1), copy(x = x - 1), copy(x = x + 1))
  }

  fun neighbours(includeDiagonals: Boolean = true): List<Point> {
    return if (includeDiagonals) diagonalNeighbours() + cardinalNeighbours()
    else cardinalNeighbours()
  }
}
