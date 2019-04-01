package com.github.ecolban.functiondecorators.utils

import java.io.File
import java.util.concurrent.ThreadLocalRandom


private fun generateTree(depth: Int): Sequence<String> = sequence {
    for (i in 1..depth) {
        yield(generateRow(i))
    }
}

fun generateRow(lineLength: Int): String {
    val random = ThreadLocalRandom.current()
    return (1..lineLength)
        .map { random.nextInt(1, 100) }
        .joinToString(separator = " ")
}


private fun printTree(depth: Int): Unit {
    File("res/Tree_$depth.txt").printWriter().use { out ->
        for (line in generateTree(depth)) out.println(line)
    }
}

fun main() {
    printTree(2000)
}

fun parseTree(treeFile: String): Array<IntArray> = File("res/$treeFile").useLines { seq ->
    seq.map { line ->
        line.split(" ")
            .map { it.toInt() }
            .toIntArray()
    }.toList().toTypedArray()
}


