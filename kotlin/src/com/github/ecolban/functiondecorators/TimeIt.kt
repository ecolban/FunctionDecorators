package com.github.ecolban.functiondecorators

import java.lang.System.currentTimeMillis as now

fun<Y> (() -> Y).timeIt(): () -> Y = object : () -> Y {
    override operator fun invoke(): Y {
        val start = now()
        val y = this@timeIt()
        println("Time elapsed = ${now() - start} ms")
        return y
    }
}

fun<X, Y> ((X) -> Y).timeIt(message: String = ""): (X) -> Y = object : (X) -> Y {
    override operator fun invoke(x: X): Y {
        val start = now()
        val y = this@timeIt(x)
        val prefix = if (message.isEmpty()) "" else "$message -- "
        println("${prefix}Time elapsed = ${now() - start} ms")
        return y
    }
}

fun<X1, X2, Y> ((X1, X2) -> Y).timeIt(message: String = ""): (X1, X2) -> Y = object : (X1, X2) -> Y {
    override operator fun invoke(x1: X1, x2: X2): Y {
        val start = now()
        val y = this@timeIt(x1, x2)
        val prefix = if (message.isEmpty()) "" else "$message -- "
        println("${prefix}Time elapsed = ${now() - start} ms")
        return y
    }
}