package com.github.ecolban.functiondecorators

import java.util.concurrent.ConcurrentHashMap


fun <X, Y> ((X) -> Y).memoizeWrong(): (X) -> Y {

    return object : (X) -> Y {

        val memo: MutableMap<X, Y> = ConcurrentHashMap()

        override fun invoke(x: X): Y = memo.getOrPut(x) { this@memoizeWrong(x) }
    }
}

fun <X1, X2, Y> ((X1, X2) -> Y).memoizeWrong(): (X1, X2) -> Y {

    return object : (X1, X2) -> Y {

        val memo: MutableMap<Pair<X1, X2>, Y> = ConcurrentHashMap()

        override fun invoke(x1: X1, x2: X2): Y = memo.getOrPut(Pair(x1, x2)) { this@memoizeWrong(x1, x2) }
    }
}

