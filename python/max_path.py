import time
from functools import lru_cache


def parse_tree(file_name):
    with open(f'../res/{file_name}', 'r') as f:
        tree = []
        for line in f.readlines():
            tree.append([int(n) for n in line[:-1].split(' ')])

    return tree


tree = parse_tree("Tree_100.txt")


def timeit(f):
    def h(*args):
        ts = time.time()
        result = f(*args)
        te = time.time()
        print('%r  %2.2f ms' % (f.__name__, (te - ts) * 1000))
        return result

    return h


def memoize(f):
    memo = {}

    def h(*args):
        if args not in memo:
            memo[args] = f(*args)
        return memo[args]

    return h

@timeit
def solution_simple(tree):

    @memoize
    def max_path(row, col):
        if row == len(tree) - 1:
            return tree[row][col]
        else:
            return tree[row][col] + \
                   max(max_path(row + 1, col), max_path(row + 1, col + 1))

    return max_path(0, 0)


@timeit
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


if __name__ == "__main__":
    print(solution_simple(tree))
