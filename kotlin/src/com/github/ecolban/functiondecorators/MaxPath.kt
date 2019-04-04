package com.github.ecolban.functiondecorators

import java.util.concurrent.ConcurrentHashMap
import kotlin.math.max

fun solutionSimple(tree: Array<IntArray>): Int {

    fun maxPath(row: Int, col: Int): Int =
        if (row == tree.size - 1) {
            tree[row][col]
        } else {
            tree[row][col] + max(maxPath(row + 1, col), maxPath(row + 1, col + 1))
        }
    return maxPath(0, 0)
}


fun solutionMemoized(tree: Array<IntArray>): Int {

    val memo = ConcurrentHashMap<Pair<Int, Int>, Int>()

    fun maxPath(row: Int, col: Int): Int = memo.getOrPut(Pair(row, col)) {
        if (row < tree.size - 1) {
            tree[row][col] + max(maxPath(row + 1, col), maxPath(row + 1, col + 1))
        } else {
            tree[row][col]
        }
    }

    return maxPath(0, 0)
}

fun solutionMemoizedWrong(tree: Array<IntArray>): Int {

    fun maxPath(row: Int, col: Int): Int =
        if (row == tree.size - 1) {
            tree[row][col]
        } else {
            tree[row][col] + max(maxPath(row + 1, col), maxPath(row + 1, col + 1))
        }

    return ::maxPath.memoizeWrong()(0, 0)
}

fun solution(tree: Array<IntArray>): Int {

    fun wrapper(f: (Int, Int) -> Int): (Int, Int) -> Int {
        fun maxPath(row: Int, col: Int): Int {
            return if (row + 1 == tree.size) {
                tree[row][col]
            } else {
                tree[row][col] + max(f(row + 1, col), f(row + 1, col + 1))
            }
        }
        return ::maxPath
    }

    return ::wrapper.memoize()(0, 0)
}

fun solutionRef(tree: Array<IntArray>): Int? = tree.reduce { acc, rowIn ->
    val rowOut = IntArray(rowIn.size)
    rowIn.forEachIndexed { i, value ->
        rowOut[i] = value + when (i) {
            0 -> acc[i]
            acc.size -> acc[i - 1]
            else -> max(acc[i - 1], acc[i])
        }
    }
    rowOut
}.max()
