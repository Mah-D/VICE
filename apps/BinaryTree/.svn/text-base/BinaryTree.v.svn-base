/*
 * This program implements a simple (unbalanced) binary tree,
 * representing each node as an object. The initializer builds
 * a tree for a set of randomly generated integers; tree search
 * is used as a benchmark for execution performance at runtime.
 *
 * @author Akop Palyan
 * @author Ben L. Titzer
 */
program BinaryTree {
	entrypoint main = BinaryTree.start;
}

class Value {
	field value: int;

	constructor(v: int) {
		value = v;
	}
}

class Node {
	field left: Node;
	field right: Node;
	field key: int;
	field value: Value;

	constructor(k: int) {
		key = k;
		value = null;
		left = null;
		right = null;
	}
}

class Tree {
	field root: Node = null;

	method insert(v: Value) {
		local n: Node;
		if (root == null) {
			root = new Node(v.value);
			root.value = v;
		} else {
			n = insertAt(root, v.value);
			n.value = v;
		}
	}

	method insertAt(n: Node, k: int): Node {
		if (k <= n.key) {
			if (n.left == null) {
				n.left = new Node(k);
				return n.left;
			} else {
				return insertAt(n.left, k);
			}
		} else {
			if (n.right == null) {
				n.right = new Node(k);
				return n.right;
			} else {
				return insertAt(n.right, k);
			}
		}
	}

	method search(k: int): Value {
		return searchAt(root, k);
	}

	method searchAt(n: Node, k: int): Value {
		if (n == null) {
			return null;
		} else if (k < n.key) {
			return searchAt(n.left, k);
		} else if (k > n.key) {
			return searchAt(n.right, k);
		} else {
			return n.value;
		}
	}
}

component BinaryTree {
	field seed: int = 1;
	field tree: Tree = new Tree();
	field v: Value = null;

	constructor() {
		local i: int;

		for (i = 0; i < 50; i++) {
			tree.insert(new Value(rand() % 200));
		}
	}

	method start() {
		local i: int, j: int;
		local cumul: int = 0;

		for (i = 0; i < 100; i++) {
			for (j = 0; j < 200; j++) {
				v = tree.search(j);
				if ( v != null ) cumul += v.value;
			}
		}
	}

	method rand(): int {
		seed = 1664525 * seed + 1013904223;
		return seed;
	}
}
