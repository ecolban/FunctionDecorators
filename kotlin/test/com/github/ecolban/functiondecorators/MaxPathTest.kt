package com.github.ecolban.functiondecorators

import com.github.ecolban.functiondecorators.utils.parseTree
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import kotlin.math.max

internal class MaxPathTest {

    @Test
    fun `test solutionSimple`() {
        val tree = parseTree("Tree_30.txt")
        val expected = solutionRef(tree)
        assertEquals(expected, ::solutionSimple.timeIt()(tree))
    }

    @Test
    fun `test solutionMemoized`() {
        val tree = parseTree("Tree_100.txt")
        val expected = solutionRef(tree)
        assertEquals(expected, ::solutionMemoized.timeIt()(tree))
    }

    @Test
    fun `test maxPathNaive with wrong memoization`() {
        val tree = parseTree("Tree_30.txt")
        val expected = solutionRef(tree)
        assertEquals(expected, ::solutionMemoizedWrong.timeIt()(tree))
    }

    @Test
    fun `test maxPath with correct memoization`() {
        val tree = parseTree("Tree_100.txt")
        val expected = solutionRef(tree)
        assertEquals(expected, ::solution.timeIt()(tree))
    }

}