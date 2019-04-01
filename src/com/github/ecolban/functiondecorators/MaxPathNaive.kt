package com.github.ecolban.functiondecorators

import com.github.ecolban.functiondecorators.utils.parseTree
import kotlin.math.max
import java.lang.System.currentTimeMillis as now


private val tree = parseTree("Tree_30.txt")

private fun maxPath(row: Int, col: Int): Int =
    if (row == tree.size - 1) {
        tree[row][col]
    } else {
        tree[row][col] + max(maxPath(row + 1, col), maxPath(row + 1, col + 1))
    }


fun main() {
    println(TimeIt2(::maxPath)(0, 0))
    println(::maxPath.timeIt()(0, 0))
}