package common.search

import common.collections.JavaPriorityQueueWrapper

fun <T> dijkstraShortestPath(start: T, end: T, getEdges: (T) -> Iterable<Pair<T, Int>>): List<T> {
  val dist = mutableMapOf<T, Int>().withDefault { Int.MAX_VALUE }
  val prev = mutableMapOf<T, T>()
  dist[start] = 0
  val queue = JavaPriorityQueueWrapper<T, Int>()
  queue.addWithPriority(start, 0)
  while (!queue.isEmpty() && queue.peek() != end) {
    val current = queue.extractMinimum()
    for ((neighbour, weight) in getEdges(current)) {
      val alternateDistance = dist.getValue(current) + weight
      if (alternateDistance < dist.getValue(neighbour)) {
        dist[neighbour] = alternateDistance
        prev[neighbour] = current
        if (queue.contains(neighbour)) {
          queue.decreasePriority(neighbour, alternateDistance)
        } else {
          queue.addWithPriority(neighbour, alternateDistance)
        }
      }
    }
  }
  val path = mutableListOf<T>()
  var vertex: T? = end
  if (vertex in prev || vertex == start) {
    while (vertex != null) {
      path.add(0, vertex)
      vertex = prev[vertex]
    }
  }
  return path
}
