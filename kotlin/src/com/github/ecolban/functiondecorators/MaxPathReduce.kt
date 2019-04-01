package com.github.ecolban.functiondecorators

import com.github.ecolban.functiondecorators.utils.parseTree
import kotlin.math.max
import java.lang.System.currentTimeMillis as now


private fun maxPath(tree: Array<IntArray>): Int? {
    val maxRow: IntArray = tree.reduce { acc, rowIn ->
        val rowOut = IntArray(rowIn.size)
        rowIn.forEachIndexed { i, value ->
            rowOut[i] = value + when (i) {
                0 -> acc[i]
                acc.size -> acc[i - 1]
                else -> max(acc[i - 1], acc[i])
            }
        }
        rowOut
    }
    return maxRow.max()
}


fun main() {
    val tree = parseTree("Tree_2000.txt")
    println(::maxPath.timeIt()(tree))
}