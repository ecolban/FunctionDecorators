package com.github.ecolban.functiondecorators.utils

import java.io.File
import java.nio.file.Paths
import java.util.concurrent.ThreadLocalRandom


private const val SEPARATOR = " "

private fun generateTree(depth: Int): Sequence<String> = sequence {
    for (i in 1..depth) yield(generateRow(i))
}

fun generateRow(lineLength: Int): String {
    val random = ThreadLocalRandom.current()
    return (1..lineLength)
        .map { random.nextInt(1, 100) }
        .joinToString(separator = SEPARATOR)
}


private fun printSequence(pathName: String, seq: Sequence<String>) {
    File(pathName).printWriter().use { for (line in seq) it.println(line) }
}

fun main() {
    val depth = 1000 // /Users/erikc/IdeaProjects/FunctionDecorators/kotlin/res
    printSequence("res/Tree_$depth.txt", generateTree(depth))
}

fun parseTree(treeFile: String): Array<IntArray> {
    val uri = Thread.currentThread().contextClassLoader.getResource(treeFile).toURI()
    return Paths.get(uri).toFile().useLines { seq ->
        seq.map { line ->
            line.split(SEPARATOR)
                    .map { it.toInt() }
                    .toIntArray()
        }.toList().toTypedArray()
    }
}