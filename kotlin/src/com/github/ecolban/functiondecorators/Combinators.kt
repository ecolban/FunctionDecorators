package com.github.ecolban.functiondecorators

import java.util.concurrent.ConcurrentHashMap

class YCombinator<X : Any, Y : Any>(val f: ((X) -> Y) -> (X) -> Y) : (X) -> Y {

    val function = f(this)

    override operator fun invoke(u: X): Y {
        return function(u)
    }

}

fun <X, Y> (((X) -> Y) -> (X) -> Y).yCombinator(): (X) -> Y {

    return object : (X) -> Y {

        override fun invoke(x: X): Y {
            return this@yCombinator(this)(x)
        }
    }
}

fun <X1, X2, Y> (((X1, X2) -> Y) -> (X1, X2) -> Y).yCombinator(): (X1, X2) -> Y {

    return object : (X1, X2) -> Y {

        override fun invoke(x1: X1, x2: X2): Y {
            return this@yCombinator(this)(x1, x2)
        }
    }
}

class Memoize<X : Any, Y : Any>(val form: ((X) -> Y) -> (X) -> Y) : (X) -> Y {

    private val memo: MutableMap<X, Y> = ConcurrentHashMap()

    override operator fun invoke(u: X): Y = memo.getOrPut(u) { form(this)(u) }

}

fun <X, Y> (((X) -> Y) -> (X) -> Y).memoize(): (X) -> Y {

    return object : (X) -> Y {

        val memo: MutableMap<X, Y> = ConcurrentHashMap()

        override fun invoke(x: X): Y = memo.getOrPut(x) { this@memoize(this)(x) }
    }
}

fun <X1, X2, Y> (((X1, X2) -> Y) -> (X1, X2) -> Y).memoize(): (X1, X2) -> Y {

    return object : (X1, X2) -> Y {

        val memo: MutableMap<Pair<X1, X2>, Y> = ConcurrentHashMap()

        override fun invoke(x1: X1, x2: X2): Y = memo.getOrPut(Pair(x1, x2)) { this@memoize(this)(x1, x2) }
    }
}

