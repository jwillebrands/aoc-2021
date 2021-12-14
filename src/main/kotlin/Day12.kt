package day12

import java.io.File

data class Cave(val name: String) {
    val isBig: Boolean = name.none { it > 'Z' }
}

class UndirectedGraph<T>(val nodes: Set<T>, val edges: Map<T, Set<T>>) {
    companion object {
        class Builder<T> {
            private var nodes = mutableSetOf<T>()
            private var edges = mutableMapOf<T, MutableSet<T>>()

            fun addNode(node: T): Builder<T> {
                nodes.add(node)
                return this
            }

            fun addEdgeBetween(first: T, second: T): Builder<T> {
                edges.getOrPut(first) { mutableSetOf() }.add(second)
                edges.getOrPut(second) { mutableSetOf() }.add(first)
                return this
            }

            fun build(): UndirectedGraph<T> {
                return UndirectedGraph(nodes.toSet(), edges.mapValues { it.value.toSet() })
            }
        }

        fun <T> builder(): Builder<T> {
            return Builder()
        }
    }
}

fun parseGraph(lines: Iterable<String>): UndirectedGraph<Cave> {
    return lines.map { it.split("-", limit = 2) }
        .map { Cave(it[0]) to Cave(it[1]) }
        .fold(UndirectedGraph.builder<Cave>()) { builder, (first, second) ->
            builder.addNode(first).addNode(second).addEdgeBetween(first, second)
        }.build()
}

fun <T> dfs(
    start: T,
    target: T,
    getEdges: (T, List<T>) -> Iterable<T>,
    visited: List<T> = emptyList()
): Sequence<List<T>> = sequence {
    val path: List<T> = visited + start
    if (target == start) {
        yield(path)
    } else {
        for (neighbour in getEdges(start, path)) {
            yieldAll(dfs(neighbour, target, getEdges, path))
        }
    }
}

fun solvePart1(lines: List<String>): Int {
    val graph = parseGraph(lines)
    return dfs(
        Cave("start"),
        Cave("end"),
        { node, visited -> graph.edges[node].orEmpty().filter { it.isBig || !visited.contains(it) } }).count()
}

fun solvePart2(lines: List<String>): Int {
    val graph = parseGraph(lines)
    val start = Cave("start")
    val end = Cave("end")
    return dfs(
        start,
        end,
        { node, visited ->
            graph.edges[node].orEmpty()
                .filter {
                    it.isBig || when(it) {
                        start -> false
                        end -> it !in visited
                        in visited -> visited.filterNot { n -> n.isBig }
                            .groupingBy { n -> n }
                            .eachCount()
                            .none { (_, count) -> count > 1 }
                        else -> true
                    }
                }
        }
    ).count()
}

fun main() {
    val lines = File("src/main/resources", "day12.txt").readLines()
    println("Part1: ${solvePart1(lines)}")
    println("Part2: ${solvePart2(lines)}")
}