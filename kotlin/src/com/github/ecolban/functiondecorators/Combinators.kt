package com.github.ecolban.functiondecorators

import java.util.concurrent.ConcurrentHashMap

typealias F1<X, Y> = (X) -> Y
typealias F2<X1, X2, Y> = (X1, X2) -> Y

fun <X, Y> ((F1<X, Y>) -> F1<X, Y>).yCombinator(): F1<X, Y> {

    return object : (X) -> Y {

        override fun invoke(x: X): Y {
            return this@yCombinator(this)(x)
        }
    }
}

fun <X1, X2, Y> ((F2<X1, X2, Y>) -> F2<X1, X2, Y>).yCombinator(): F2<X1, X2, Y> {

    return object : F2<X1, X2, Y> {

        override fun invoke(x1: X1, x2: X2): Y {
            return this@yCombinator(this)(x1, x2)
        }
    }
}

fun <X, Y> ((F1<X, Y>) -> F1<X, Y>).memoize(): F1<X, Y> {

    return object : F1<X, Y> {

        val memo: MutableMap<X, Y> = ConcurrentHashMap()

        override fun invoke(x: X): Y = memo.getOrPut(x) { this@memoize(this)(x) }
    }
}

fun <X1, X2, Y> ((F2<X1, X2, Y>) -> F2<X1, X2, Y>).memoize(): F2<X1, X2, Y> {

    return object : F2<X1, X2, Y> {

        val memo: MutableMap<Pair<X1, X2>, Y> = ConcurrentHashMap()

        override fun invoke(x1: X1, x2: X2): Y = memo.getOrPut(Pair(x1, x2)) { this@memoize(this)(x1, x2) }
    }
}

fun <X, Y> ((F1<X, Y>) -> F1<X, Y>).trace(name: String = ""): (F1<X, Y>) -> F1<X, Y> {

    return object :(F1<X, Y>) -> F1<X, Y> {

        override fun invoke(f: F1<X, Y>): F1<X, Y> {
            return {x: X ->
                println("$name -- in: $x")
                val y = this@trace(f)(x)
                println("$name -- in: $x, out: $y")
                y
            }
        }
    }
}

fun <X1, X2, Y> ((F2<X1, X2, Y>) -> F2<X1, X2, Y>).trace2(name: String = ""): (F2<X1, X2, Y>) -> F2<X1, X2, Y> {

    return object :(F2<X1, X2, Y>) -> F2<X1, X2, Y> {

        override fun invoke(f: F2<X1, X2, Y>): F2<X1, X2, Y> {
            return {x1: X1, x2: X2 ->
                println("$name -- in: $x1, $x2")
                val y = this@trace2(f)(x1, x2)
                println("$name -- in: $x1, $x2, out: $y")
                y
            }
        }
    }
}



