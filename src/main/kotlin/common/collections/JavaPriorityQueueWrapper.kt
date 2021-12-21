package common.collections

class JavaPriorityQueueWrapper<T, V : Comparable<V>> : PriorityQueue<T, V> {
  private val priorityQueue = java.util.PriorityQueue(this::compareWeights)
  private val weights = mutableMapOf<T, V>()

  private fun compareWeights(lhs: T, rhs: T): Int {
    return weights[lhs]!!.compareTo(weights[rhs]!!)
  }

  override fun decreasePriority(value: T, priority: V) {
    weights[value] = priority
    priorityQueue.remove(value)
    priorityQueue.add(value)
  }

  override fun addWithPriority(value: T, priority: V) {
    weights[value] = priority
    priorityQueue.add(value)
  }

  override fun extractMinimum(): T {
    val min = priorityQueue.remove()
    weights.remove(min)
    return min
  }

  override fun isEmpty(): Boolean {
    return priorityQueue.isEmpty()
  }

  override fun contains(value: T): Boolean {
    return priorityQueue.contains(value)
  }

  override fun peek(): T {
    return priorityQueue.peek()
  }
}
