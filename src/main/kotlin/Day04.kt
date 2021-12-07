package day4

import java.io.File

data class BingoCell(val number: Int, var marked: Boolean = false) {
  fun mark() {
    if (marked) throw IllegalStateException("Already marked")
    marked = true
  }
}

class BingoSequence(val cells: Iterable<BingoCell>) {
  val allMarked
    get() = cells.all { it.marked }
}

class BingoBoard
private constructor(val cellsByNumber: Map<Int, BingoCell>, val sequences: List<BingoSequence>) {

  fun mark(number: Int) {
    cellsByNumber.get(number)?.mark()
  }

  fun hasBingo(): Boolean {
    return sequences.any { it.allMarked }
  }

  fun sumUnmarkedCells(): Int {
    return cellsByNumber.values.filterNot { it.marked }.sumOf { it.number }
  }

  companion object {
    private fun buildSequences(grid: List<List<BingoCell>>): List<BingoSequence> {
      val rows = grid.map(::BingoSequence)
      val columns =
          (0..grid.get(0).size - 1)
              .map { column -> (0..grid.size - 1).map { row -> grid.get(row).get(column) } }
              .map(::BingoSequence)
      return rows + columns
    }

    fun fromText(lines: Iterable<String>): BingoBoard {
      val grid =
          lines
              .map { line -> line.trim().split(Regex("\\s+")).map { BingoCell(it.toInt()) } }
              .toList()
      val cellsByNumber = grid.flatMap { it }.map { it.number to it }.toMap()
      val sequences = buildSequences(grid)
      return BingoBoard(cellsByNumber, sequences)
    }
  }
}

data class WinningDraw(val number: Int, val board: BingoBoard) {
  val score: Long
    get() = number.toLong() * board.sumUnmarkedCells()
}

class BingoGame(val drawSequence: List<Int>, val boards: List<BingoBoard>) {

  fun play(): WinningDraw? {
    for (number in drawSequence) {
      for (board in boards) {
        board.mark(number)
        if (board.hasBingo()) {
          return WinningDraw(number, board)
        }
      }
    }

    return null
  }

  fun lastWinningDraw(): WinningDraw? {
    var remainingBoards = boards
    for (number in drawSequence) {
      remainingBoards.forEach { it.mark(number) }
      val (finishedBoards, playingBoards) = remainingBoards.partition { it.hasBingo() }
      if (playingBoards.size == 0 && finishedBoards.size == 1) {
        return WinningDraw(number, finishedBoards[0])
      }
      remainingBoards = playingBoards
    }
    return null
  }
}

fun createBingoGame(lines: List<String>): BingoGame {
  val drawSequence = lines.get(0).split(",").map { it.toInt() }
  val boardSize: Int = lines.subList(2, lines.size).indexOf("")

  val boards =
      (2..lines.size step boardSize + 1).map {
        BingoBoard.fromText(lines.subList(it, it + boardSize))
      }
  return BingoGame(drawSequence, boards)
}

fun solvePart1(lines: List<String>): Long {
  return createBingoGame(lines).play()!!.score
}

fun solvePart2(lines: List<String>): Long {
  return createBingoGame(lines).lastWinningDraw()!!.score
}

fun main() {
  val input = File("src/main/resources", "day04-2.txt").readLines()
  println("Part 1: ${solvePart1(input)}")
  println("Part 2: ${solvePart2(input)}")
}
