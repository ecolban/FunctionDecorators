package com.github.ecolban.functiondecorators

import com.github.ecolban.functiondecorators.utils.parseTree
import java.util.concurrent.ConcurrentHashMap
import kotlin.math.max

private val tree = parseTree("Tree_200.txt")

private fun maxPath(row: Int, col: Int): Int {
    val memo = ConcurrentHashMap<Pair<Int, Int>, Int>()

    fun helper(row: Int, col: Int): Int = memo.getOrPut(Pair(row, col)) {
        if (row < tree.size - 1) {
            tree[row][col] + max(helper(row + 1, col), helper(row + 1, col + 1))
        } else {
            tree[row][col]
        }
    }

    return helper(row, col)
}


fun main() {
    println(::maxPath.timeIt()(0, 0))
}