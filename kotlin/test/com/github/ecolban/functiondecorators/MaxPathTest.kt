package com.github.ecolban.functiondecorators

import com.github.ecolban.functiondecorators.utils.parseTree
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import kotlin.math.max

internal class MaxPathTest {

    @Test
    fun `test maxPathNaive`() {
        val tree = parseTree("Tree_30.txt")
        val expected = reduceMaxPath(tree)
        assertEquals(expected, ::maxPathNaive.timeIt()(tree))
    }

    @Test
    fun `test maxPathMemoized`() {
        val tree = parseTree("Tree_100.txt")
        val expected = reduceMaxPath(tree)
        assertEquals(expected, ::maxPathMemoized.timeIt()(tree))
    }

    @Test
    fun `test maxPathNaive with wrong memoization`() {
        val tree = parseTree("Tree_30.txt")
        val expected = reduceMaxPath(tree)
        assertEquals(expected, ::maxPathNaiveMemoizedWrong.timeIt()(tree))
    }

    @Test
    fun `test maxPath with correct memoization`() {
        val tree = parseTree("Tree_100.txt")
        val expected = reduceMaxPath(tree)
        assertEquals(expected, ::maxPath.timeIt()(tree))
    }

    @Test
    fun `test memoize on maxPath again`() {
        val tree = parseTree("Tree_30.txt")

        fun maxPath(f: (Int, Int) -> Int): (Int, Int) -> Int {
            fun h(row: Int, col: Int): Int {
                return if (row + 1 == tree.size) {
                    tree[row][col]
                } else {
                    tree[row][col] + max(f(row + 1, col), f(row + 1, col + 1))
                }
            }
            return ::h
        }
        val expected = reduceMaxPath(tree)
        assertEquals(expected, ::maxPath.memoize().timeIt()(0, 0))
    }

}