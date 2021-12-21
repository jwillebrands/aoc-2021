package common.collections

import common.geometry.Point

class Grid<T>(private val data: List<List<T>>) {
  val height = data.size
  val width = data[0].size

  operator fun get(p: Point): T {
    return data[p.y][p.x]
  }

  operator fun get(x: Int, y: Int): T {
    return data[y][x]
  }

  operator fun contains(p: Point): Boolean {
    return p.x in (0 until width) && p.y in (0 until height)
  }

  fun neighbouringCells(p: Point, includeDiagonals: Boolean = false): List<Point> {
    return p.neighbours(includeDiagonals).filter { it in this }
  }

  val points
    get() = (0 until height).flatMap { y -> (0 until width).map { x -> Point(x, y) } }

  val values
    get() = points.map { this[it] }

  val entries
    get() = points.map { it to this[it] }

  companion object {
    fun <T> fromText(
        lines: Iterable<String>,
        mapper: (String) -> T,
        tokenizer: (String) -> Iterable<String> = { it.chunked(1) }
    ): Grid<T> {
      return Grid(lines.map { tokenizer(it).map(mapper) })
    }
  }
}
