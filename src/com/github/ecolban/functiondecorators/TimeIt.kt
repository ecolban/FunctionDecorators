package com.github.ecolban.functiondecorators

import java.lang.System.currentTimeMillis as now

class TimeIt<U : Any, V : Any>(val f: (U) -> V) : (U) -> V {

    override operator fun invoke(u: U): V {
        val start = now()
        val result = f(u)
        println("Time elapsed = ${now() - start} ms")
        return result
    }
}

class TimeIt2<X1 : Any, X2: Any, Y : Any>(val f: (X1, X2) -> Y) : (X1, X2) -> Y {

    override operator fun invoke(x1: X1, x2: X2): Y {
        val start = now()
        val result = f(x1, x2)
        println("Time elapsed = ${now() - start} ms")
        return result
    }
}

fun<Y> (() -> Y).timeIt(): () -> Y = object : () -> Y {
    override operator fun invoke(): Y {
        val start = now()
        val y = this@timeIt()
        println("Time elapsed = ${now() - start} ms")
        return y
    }
}

fun<X, Y> ((X) -> Y).timeIt(): (X) -> Y = object : (X) -> Y {
    override operator fun invoke(x: X): Y {
        val start = now()
        val y = this@timeIt(x)
        println("Time elapsed = ${now() - start} ms")
        return y
    }
}

fun<X1, X2, Y> ((X1, X2) -> Y).timeIt(): (X1, X2) -> Y = object : (X1, X2) -> Y {
    override operator fun invoke(x1: X1, x2: X2): Y {
        val start = now()
        val y = this@timeIt(x1, x2)
        println("Time elapsed = ${now() - start} ms")
        return y
    }
}