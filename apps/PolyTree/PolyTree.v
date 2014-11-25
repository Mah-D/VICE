/*
 * This program implements a simple (unbalanced) binary tree,
 * representing each node as an object. The initializer builds
 * a tree for a set of randomly generated integers; tree search
 * is used as a benchmark for execution performance at runtime.
 *
 * @author Akop Palyan
 * @author Ben L. Titzer
 */
program PolyTree {
	entrypoint main = PolyTree.start;
}

class Value {
	field value: int;

	constructor(v: int) {
		value = v;
	}
}

class Node<T> {
	field left: Node<T>;
	field right: Node<T>;
	field key: int;
	field value: T;

	constructor(k: int) {
		key = k;
		left = null;
		right = null;
	 }
}

class Tree<T> {
	field root: Node<T> = null;
	field def: T;

	constructor(d: T) {
		def = d;
	}

	method insert(k: int, v: T) {
		local n = (root == null) ? root = new Node<T>(k) : insertAt(root, k);
		n.value = v;
	}

	method insertAt(n: Node<T>, k: int): Node<T> {
		if (k <= n.key) {
			if (n.left == null) {
				n.left = new Node<T>(k);
				return n.left;
			} else {
				return insertAt(n.left, k);
			}
		} else {
			if (n.right == null) {
				n.right = new Node<T>(k);
				return n.right;
			} else {
				return insertAt(n.right, k);
			}
		}
	}

	method search(k: int): T {
		return searchAt(root, k);
	}

	method searchAt(n: Node<T>, k: int): T {
		if (n == null) {
			return def;
		} else if (k < n.key) {
			return searchAt(n.left, k);
		} else if (k > n.key) {
			return searchAt(n.right, k);
		} else {
			return n.value;
		}
	}
}

component PolyTree {
	field seed: int = 1;
	field tree_val: Tree<Value> = new Tree<Value>(null);
	field tree_int: Tree<int> = new Tree<int>(0);
	field v: Value = null;

	constructor() {
		local i: int;

		for (i = 0; i < 50; i++) {
			local k = rand() % 200;
			//tree_val.insert(k, new Value(k));
			tree_int.insert(k, k);
		}
	}

	method start() {
		local i: int, j: int;
		local cumul1 = 0;
		local cumul2 = 0;
		for (i = 0; i < 100; i++) {
			for (j = 0; j < 200; j++) {
				cumul2 += tree_int.search(j);
			}
		}
	}

	method rand(): int {
		seed = 1664525 * seed + 1013904223;
		return seed;
	}
}
