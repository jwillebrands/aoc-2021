import java.io.File

data class Submarine(val hpos: Int = 0, val depth: Int = 0, val aim: Int = 0)

typealias SubmarineCommand = (Submarine, Int) -> Submarine

typealias CommandFactory = (String) -> SubmarineCommand

fun executeCommands(lines: List<String>, commandFactory: CommandFactory): Submarine {
  return lines.map { it.split(" ") }.fold(Submarine()) { sub, cmd ->
    commandFactory(cmd[0])(sub, cmd[1].toInt())
  }
}

fun solvePart1(lines: List<String>): Int {
  val commandFactory: CommandFactory = { command ->
    when (command) {
      "forward" -> { sub, amount -> sub.copy(hpos = sub.hpos + amount) }
      "up" -> { sub, amount -> sub.copy(depth = sub.depth - amount) }
      "down" -> { sub, amount -> sub.copy(depth = sub.depth + amount) }
      else -> throw IllegalArgumentException("Unknown command ${command}")
    }
  }
  return with(executeCommands(lines, commandFactory)) { hpos * depth }
}

fun solvePart2(lines: List<String>): Int {
  val commandFactory: CommandFactory = { command ->
    when (command) {
      "forward" -> { sub, amount ->
            sub.copy(hpos = sub.hpos + amount, depth = sub.depth + sub.aim * amount)
          }
      "up" -> { sub, amount -> sub.copy(aim = sub.aim - amount) }
      "down" -> { sub, amount -> sub.copy(aim = sub.aim + amount) }
      else -> throw IllegalArgumentException("Unknown command ${command}")
    }
  }
  return with(executeCommands(lines, commandFactory)) { hpos * depth }
}

fun main() {
  val input = File("src/main/resources", "day02.txt").readLines()
  println("Part 1: ${solvePart1(input)}")
  println("Part 2: ${solvePart2(input)}")
}
