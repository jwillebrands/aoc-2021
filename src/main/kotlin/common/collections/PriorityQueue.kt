package common.collections

interface PriorityQueue<T, V : Comparable<V>> {
  fun decreasePriority(value: T, priority: V)
  fun addWithPriority(value: T, priority: V)
  fun extractMinimum(): T
  operator fun contains(value: T): Boolean
  fun isEmpty(): Boolean
  fun peek(): T
}
