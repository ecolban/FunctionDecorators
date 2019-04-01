package com.github.ecolban.functiondecorators

import java.util.concurrent.ConcurrentHashMap

class Memoize<U : Any, V : Any>(val f: (U, (U) -> V) -> V) : (U) -> V {

    private val memo: MutableMap<U, V> = ConcurrentHashMap()

    override operator fun invoke(u: U): V = memo.getOrPut(u) { f(u, this) }

}

fun <X, Y> ((X, (X) -> Y) -> Y).memoize(): (X) -> Y {

    return object : (X) -> Y {

        val memo: MutableMap<X, Y> = ConcurrentHashMap()

        override fun invoke(x: X): Y = memo.getOrPut(x) { this@memoize(x, this) }
    }
}

fun <X1, X2, Y> ((X1, X2, (X1, X2) -> Y) -> Y).memoize(): (X1, X2) -> Y {

    return object : (X1, X2) -> Y {

        val memo: MutableMap<Pair<X1, X2>, Y> = ConcurrentHashMap()

        override fun invoke(x1: X1, x2: X2): Y = memo.getOrPut(Pair(x1, x2)) { this@memoize(x1, x2, this) }
    }
}

