import java.io.File

fun countIncreasingMeasurements(measurements: List<Int>): Int {
    return measurements
        .windowed(2)
        .sumOf { (first, second) ->
            @Suppress("USELESS_CAST")
            // cast is need to clarify ambiguity: https://youtrack.jetbrains.com/issue/KT-46360
            if (first < second) 1 as Int else 0
        }
}

fun countIncreasingMeasurementsWindowed(measurements: List<Int>): Int {
    return countIncreasingMeasurements(measurements.windowed(3, transform = { it.sum() }))
}

fun main() {
    val input = File("src/main/resources", "day01.txt").readLines().map { it.toInt() }
    println("Part 1 : ${countIncreasingMeasurements(input)}")
    println("Part 2 : ${countIncreasingMeasurementsWindowed(input)}")
}