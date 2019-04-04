# Function Decorators

This project is about trying to replicate decorators, as they are known in Python, 
in Kotlin. The following problem will be used to illustrate the solutions sought 
and found.

## The problem

Given a pyramid of numbers such as:

```
344
743 530
289 112 269
423 190 841 701
56 453 203 937 681
829 364 963 393 239 641
447 127 43 404 36 581 387
880 73 941 770 618 149 892 758
65 339 639 232 471 417 149 154 850
631 33 942 761 925 940 441 919 551 870
```

Starting at the top, move along a path to the bottom row while summing the numbers 
on the path. From any number, you can only move to one of the two numbers below it. 
For example, from 112 on the third row, you can only move to 190 or 841. What is 
the maximal sum you can get? 


## Short introduction to Python decorators

We can divide the problem into sub-problems `max_path(row, col)`: What is the 
maximal sum of numbers along a path starting at `(row, col)` and ending at the 
bottom row?
 
A simple solution in Python (assuming that the number pyramid, or tree, as we have 
called it in the code, is given as a list of lists) would be:

```Python
def solution_simple(tree):
    
    def max_path(row, col):
        if row == len(tree) - 1:
            return tree[row][col]
        else:
            return tree[row][col] + \
                   max(max_path(row + 1, col), max_path(row + 1, col + 1))
                   
    return max_path(0, 0)
```

Although correct, this solution is very slow. The reason is that sub-problem
instances are computed many times. To correct for this, we can introduce 
_memoization_.

```Python
def solution_memoized(tree):

    memo = {}

    def max_path(row, col):
        if (row, col) not in memo:
            if row == len(tree) - 1:
                res = tree[row][col]
            else:
                res = tree[row][col] + \
                      max(max_path(row + 1, col), max_path(row + 1, col + 1))
            memo[(row, col)] = res
        return memo[(row, col)]

    return max_path(0, 0)

```

This solution runs much faster, but it has it's drawbacks. First, the code related
to memoization clutters up the code that defines the functionality and makes it 
less readable. Secondly, if we were to apply this pattern whenever memoization was
needed, then we would be repeating ourselves. Wouldn't
it be great if there were a higher order function that would map `max_path` in the
first solution to `max_path` in the memoized solution? This is where decorators in
Python come into play. 

First we need the higher order function:

```Python
def memoize(f):
    memo = {}

    def h(*args):
        if args not in memo:
            memo[args] = f(*args)
        return memo[args]

    return h
```

Next we add the word "`@memoize`" to `solution_simple` like this:

```Python
def solution_simple(tree):
    
    @memoize
    def max_path(row, col):
        ...
```

This has the effect of applying the higher order function `memoize` to `max_path`
resulting in a function, let's call it `h`, and _replacing_ `max_path` with `h`,
such that wherever `max_path` is called, it's `h` that gets called. This turns 
`solution_simple` into `solution_memoized`. 
 
In [max_path](https://github.com/ecolban/FunctionDecorators/blob/master/python/max_path.py)
we show another example of a decorator: `timeit`. There are many other use cases 
for decorators.

(Note: There exists a decorator in the Python libraries called `lru_cache`, which we 
could have used instead of `memoize`.)

## Implementing function decorators in Kotlin

Since a decorator is nothing but a higher order function (HOF) and Kotlin allows
for HOF, it should be simple to implement them in Kotlin. As we will see, it's 
_replacing_ the function that the decorator is applied to with the resulting 
decorated function, that is the tricky part.

A first example of a decorator is shown in 
[TimeIt.kt](https://github.com/ecolban/FunctionDecorators/blob/master/kotlin/src/com/github/ecolban/functiondecorators/TimeIt.kt)

`timeIt` is defined as an extension function of the function that it decorates. It 
needs to be overloaded so that it can be applied to functions with different 
number of arguments.

If `timeIt` is applied to a function `f`, then `f` and `f.timeIt` are two distinct
functions, which may coexist in the environment, unlike in Python, where the second
replaces the first. If `f` is recursive, then `f.timeIt()` will call `f` and not
itself. This is OK for a decorator such as `timeIt`; normally we only want to time
the top level call, not each recursive call.

In [MemoizeWrong.kt](https://github.com/ecolban/FunctionDecorators/blob/master/kotlin/src/com/github/ecolban/functiondecorators/MemoizeWrong.kt),
we have the Kotlin equivalent of `memoize` in the Python code. However, decorating
a function with this function, does not have the same effect as decorating a 
function with `memoize` in Python. This is illustrated by `solutionMemoizedWrong`
in 
[MaxPath.kt](https://github.com/ecolban/FunctionDecorators/blob/master/kotlin/src/com/github/ecolban/functiondecorators/MaxPath.kt),
If `memoizeWrong()` is applied to a recursive function `maxPath`, then 
`maxPath.memoizeWrong()` will call `maxPath` and not `maxPath.memoizeWrong()`. As
 a result, only the top level call to `f.memoiseWrong()` is entered into the
`memo`, which is useless as it's the recursive calls that cause the inefficiency.

To get around this problem, I've replaced the recursive calls to `maxPath` with a
free variable `f` to allow the `memoize` function to inject the "correct" 
memoized version of `maxPath`.

```Kotlin
    fun maxPath(row: Int, col: Int): Int {
        return if (row + 1 == tree.size) {
            tree[row][col]
        } else {
            tree[row][col] + max(f(row + 1, col), f(row + 1, col + 1))
        }
    }
```
Since this function is no longer recursive is can be given either as a __fun__ 
or a lambda; that is not of essence. Note the very close similarity with `maxPath`
in `solutionSimple`. In order to bind `f`, I place the above function inside a 
wrapper:

```Kotlin
    fun wrapper(f: (Int, Int) -> Int): (Int, Int) -> Int {
        fun maxPath(row: Int, col: Int): Int {
            return if (row + 1 == tree.size) {
                tree[row][col]
            } else {
                tree[row][col] + max(f(row + 1, col), f(row + 1, col + 1))
            }
        }
        return ::maxPath
    }

```  
In order to inject the "correct function", the `memoize` function must call 
`wrapper` with the "correct function". The "correct function" is the function that
`wrapper` returns when called with the "correct function". In other words, if `g`
is the "correct function", then `g = wrapper(g)`. In addition, `memoize` must 
apply memoization. See 
[Combinators.kt](https://github.com/ecolban/FunctionDecorators/blob/master/kotlin/src/com/github/ecolban/functiondecorators/Combinators.kt)
for an implementation of `memoize`. Note that if we remove the memoization code
from `memoize`, we get a simple Y combinator, so `memoize` is a Y combinator that
memoizes. 

I'm not completely satisfied with having to rewrite recursive functions on the 
above form in order to apply decorators. I wish that I could use `maxPath` as found
in `solutionSimple`, but have found no way do that. If you have a suggestion, 
please do not hesitate to share it with me. Even better, fork this repo, and submit
a pull request.   




 
 


