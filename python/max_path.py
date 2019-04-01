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


# @memoize
def max_path(row, col):
    if row == len(tree) - 1:
        return tree[row][col]
    else:
        return tree[row][col] + \
               max(max_path(row + 1, col), max_path(row + 1, col + 1))


def max_path_memoized(row, col):
    memo = {}

    def h(r, c):
        if (r, c) not in memo:
            if r == len(tree) - 1:
                res = tree[r][c]
            else:
                res = tree[r][c] + \
                      max(h(r + 1, c), h(r + 1, c + 1))
            memo[(r, c)] = res
        return memo[(r, c)]

    return h(row, col)


@timeit
def test_max_path():
    return max_path_memoized(0, 0)


if __name__ == "__main__":
    print(test_max_path())
