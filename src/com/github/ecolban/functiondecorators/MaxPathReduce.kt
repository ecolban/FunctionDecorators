package com.github.ecolban.functiondecorators

import com.github.ecolban.functiondecorators.utils.parseTree
import kotlin.math.max
import java.lang.System.currentTimeMillis as now


private val tree: Array<IntArray> = parseTree("Tree_2000.txt")

private fun maxPath(): Int {
    val maxRow: IntArray = tree.reduce { acc, ints ->
        val row = IntArray(ints.size)
        ints.forEachIndexed { index, n ->
            row[index] = when (index) {
                0 -> acc[0] + n
                row.size - 1 -> acc[index - 1] + n
                else -> max(acc[index - 1], acc[index]) + n
            }
        }
        row
    }
    return maxRow.max()!!
}


fun main() {
    println(::maxPath.timeIt()())
}