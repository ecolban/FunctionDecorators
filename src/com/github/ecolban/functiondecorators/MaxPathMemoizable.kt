package com.github.ecolban.functiondecorators

import com.github.ecolban.functiondecorators.utils.parseTree
import kotlin.math.max


private val tree = parseTree("Tree_600.txt")

private fun maxPath(row: Int, col: Int, f: (Int, Int) -> Int): Int =
    if (row + 1 == tree.size) {
        tree[row][col]
    } else {
        tree[row][col] + max(f(row + 1, col), f(row + 1, col + 1))
    }


fun main() {
    val memoized = ::maxPath.memoize()
//    for (col in 0 until tree[1500].size) memoized(1500, col)
//    for (col in 0 until tree[1000].size) memoized(1000, col)
//    for (col in 0 until tree[500].size) memoized(500, col)
    println(memoized.timeIt()(0, 0))
}