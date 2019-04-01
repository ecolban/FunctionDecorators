package com.github.ecolban.functiondecorators

import com.github.ecolban.functiondecorators.utils.parseTree
import kotlin.math.max


private val tree = parseTree("Tree_30.txt")

private val maxPath = { f: (Int, Int) -> Int ->
    { row: Int, col: Int ->
        if (row + 1 == tree.size) {
            tree[row][col]
        } else {
            tree[row][col] + max(f(row + 1, col), f(row + 1, col + 1))
        }
    }
}


fun main() {
    println(maxPath.yCombinator().timeIt()(0, 0))
    println(maxPath.memoize().timeIt()(0, 0))
}