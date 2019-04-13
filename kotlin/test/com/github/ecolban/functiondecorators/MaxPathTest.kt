package com.github.ecolban.functiondecorators

import com.github.ecolban.functiondecorators.utils.parseTree
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class MaxPathTest {

    @Test
    fun `test solutionSimple`() {
        val tree = parseTree("Tree_30.txt")
        val expected = solutionRef(tree)
        assertEquals(expected, ::solutionSimple.timeIt("test solutionSimple")(tree))
    }

    @Test
    fun `test solutionMemoized`() {
        val tree = parseTree("Tree_100.txt")
        val expected = solutionRef(tree)
        assertEquals(expected, ::solutionMemoized.timeIt("test solutionMemoized")(tree))
    }

    @Test
    fun `test maxPathNaive with wrong memoization`() {
        val tree = parseTree("Tree_30.txt")
        val expected = solutionRef(tree)
        assertEquals(expected, ::solutionMemoizedWrong.timeIt("test maxPathNaive with wrong memoization")(tree))
    }

    @Test
    fun `test maxPath with correct memoization`() {
        val tree = parseTree("Tree_100.txt")
        val expected = solutionRef(tree)
        assertEquals(expected, ::solution.timeIt("test maxPath with correct memoization")(tree))
    }

}