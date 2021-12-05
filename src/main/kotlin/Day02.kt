import java.io.File

data class Submarine(val hpos: Int = 0, val depth: Int = 0) {
    fun move(direction: String, amount: Int): Submarine {
       return when (direction) {
            "forward" -> copy(hpos = hpos + amount)
            "up" -> copy(depth = depth - amount)
            "down" -> copy(depth = depth + amount)
            else -> throw IllegalArgumentException("Unkown direction ${direction}")
        }
    }
}

fun solvePart1(lines: List<String>): Int {
    return with(lines.map{it.split(" ")}
    	.fold(Submarine()) {sub, cmd -> sub.move(cmd[0], cmd[1].toInt())}
	){ hpos * depth}
}

fun solvePart2(lines: List<String>): String {
  return ""
}

fun main() {
    val input = File("src/main/resources", "day02.txt").readLines()
    println("Part 1: ${solvePart1(input)}")
    println("Part 2: ${solvePart2(input)}")
}