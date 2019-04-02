package com.github.ecolban.functiondecorators

import java.util.concurrent.ConcurrentHashMap
import kotlin.math.max

fun maxPathNaive(tree: Array<IntArray>): Int {

    fun helper(row: Int, col: Int): Int =
        if (row == tree.size - 1) {
            tree[row][col]
        } else {
            tree[row][col] + max(helper(row + 1, col), helper(row + 1, col + 1))
        }
    return helper(0, 0)
}


fun maxPathMemoized(tree: Array<IntArray>): Int {

    val memo = ConcurrentHashMap<Pair<Int, Int>, Int>()

    fun helper(row: Int, col: Int): Int = memo.getOrPut(Pair(row, col)) {
        if (row < tree.size - 1) {
            tree[row][col] + max(helper(row + 1, col), helper(row + 1, col + 1))
        } else {
            tree[row][col]
        }
    }

    return helper(0, 0)
}

fun maxPathNaiveMemoizedWrong(tree: Array<IntArray>): Int {

    fun helper(row: Int, col: Int): Int =
        if (row == tree.size - 1) {
            tree[row][col]
        } else {
            tree[row][col] + max(helper(row + 1, col), helper(row + 1, col + 1))
        }

    return ::helper.memoizeWrong()(0, 0)
}

fun maxPath(tree: Array<IntArray>): Int {

    val helper = { f: (Int, Int) -> Int ->
        { row: Int, col: Int ->
            if (row + 1 == tree.size) {
                tree[row][col]
            } else {
                tree[row][col] + max(f(row + 1, col), f(row + 1, col + 1))
            }
        }
    }

    return helper.memoize()(0, 0)
}

fun reduceMaxPath(tree: Array<IntArray>): Int? = tree.reduce { acc, rowIn ->
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
