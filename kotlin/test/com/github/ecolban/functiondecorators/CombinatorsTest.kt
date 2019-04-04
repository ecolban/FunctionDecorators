package com.github.ecolban.functiondecorators

import com.github.ecolban.functiondecorators.utils.parseTree
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import kotlin.math.max

internal class CombinatorsTest {

    @Test
    fun `test Y-combinator on factorial`() {

        val factorial = { f: (Int) -> Int ->
            { n: Int -> if (n == 0) 1 else n * f(n - 1) }
        }.yCombinator()

        assertEquals(1, factorial(0))
        assertEquals(1, factorial(1))
        assertEquals(2, factorial(2))
        assertEquals(6, factorial(3))
        assertEquals(24, factorial(4))
        assertEquals(120, factorial(5))
    }

    @Test
    fun `test Y-combinator on factorial with trace and timing`() {

        val factorial = { f: (Int) -> Int ->
            { n: Int -> if (n == 0) 1 else n * f(n - 1) }
        }
            .trace(name = "factorial")
            .yCombinator()
            .timeIt(message = "test Y-combinator on factorial with trace and timing")

        assertEquals(120, factorial(5))
    }

    @Test
    fun `test memoize on factorial`() {

        val factorial = { f: (Int) -> Int ->
            { n: Int ->
                if (n == 0) 1 else n * f(n - 1)
            }
        }.memoize()

        assertEquals(1, factorial(0))
        assertEquals(1, factorial(1))
        assertEquals(2, factorial(2))
        assertEquals(6, factorial(3))
        assertEquals(24, factorial(4))
        assertEquals(120, factorial(5))
    }

    @Test
    fun `test Y-combinator on fibonacci`() {
        val fib = { f: (Int) -> Long ->
            { n: Int ->
                when (n) {
                    0 -> 0L
                    1 -> 1L
                    else -> f(n - 2) + f(n - 1)
                }
            }
        }.yCombinator()

        assertEquals(0L, fib(0))
        assertEquals(1L, fib(1))
        assertEquals(1L, fib(2))
        assertEquals(2L, fib(3))
        assertEquals(3L, fib(4))
        assertEquals(5L, fib(5))
        assertEquals(8L, fib(6))
        assertEquals(13L, fib(7))
        assertEquals(21L, fib(8))
        assertEquals(34L, fib(9))
        assertEquals(55L, fib(10))
    }

    @Test
    fun `test Y-combinator on fibonacci with timing`() {
        val fib = { f: (Int) -> Long ->
            { n: Int ->
                when (n) {
                    0 -> 0L
                    1 -> 1L
                    else -> f(n - 2) + f(n - 1)
                }
            }
        }
            .yCombinator()
            .timeIt("test Y-combinator on fibonacci with timing")

        assertEquals(102334155, fib(40))
    }

    @Test
    fun `test Y-combinator on fibonacci with trace and timing`() {
        val fib = { f: (Int) -> Long ->
            { n: Int ->
                when (n) {
                    0 -> 0L
                    1 -> 1L
                    else -> f(n - 1) + f(n - 2)
                }
            }
        }
            .trace(name = "fib")
            .yCombinator()
            .timeIt("test Y-combinator on fibonacci with trace and timing")


        assertEquals(5L, fib(5))

    }

    @Test
    fun `test memoize on fibonacci with timing`() {
        val fib = { f: (Int) -> Long ->
            { n: Int ->
                when (n) {
                    0 -> 0L
                    1 -> 1L
                    else -> f(n - 2) + f(n - 1)
                }
            }
        }
            .memoize()
            .timeIt("test memoize on fibonacci with timing")

        assertEquals(259695496911122585L, fib(85))
    }

    @Test
    fun `test memoize on fibonacci with trace`() {
        val fib = { f: (Int) -> Long ->
            { n: Int ->
                when (n) {
                    0 -> 0L
                    1 -> 1L
                    else -> f(n - 1) + f(n - 2)
                }
            }
        }
            .trace(name = "fib")
            .memoize()

        assertEquals(259695496911122585L, fib(85))

    }

    @Test
    fun `test Y-combinator on maxPath with timing`() {
        val tree = parseTree("Tree_30.txt")

        val maxPath = {f: (Int, Int) -> Int ->
            { row: Int, col: Int->
                if (row + 1 == tree.size) {
                    tree[row][col]
                } else {
                    tree[row][col] + max(f(row + 1, col), f(row + 1, col + 1))
                }
            }
        }
            .yCombinator()
            .timeIt("test Y-combinator on maxPath with timing")

        val expected = solutionRef(tree)

        assertEquals(expected, maxPath(0, 0))
    }

    @Test
    fun `test memoize on maxPath with timing`() {
        val tree = parseTree("Tree_100.txt")

        val maxPath = {f: (Int, Int) -> Int ->
            { row: Int, col: Int->
                if (row + 1 == tree.size) {
                    tree[row][col]
                } else {
                    tree[row][col] + max(f(row + 1, col), f(row + 1, col + 1))
                }
            }
        }
            .memoize()
            .timeIt("test memoize on maxPath with timing")

        val expected = solutionRef(tree)

        assertEquals(expected, maxPath(0, 0))
    }
}