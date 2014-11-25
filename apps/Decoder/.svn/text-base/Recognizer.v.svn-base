class EdgeList {
	field nodes: Node[];

	constructor(n: Node[]) {
		nodes = n;
	}

	method next(val: int, error: Node): Node {
		local n = nodes[val];
		return n != null ? n : error;
	}
}

class Node {
	method move(val: 32, error: Node): Node { return this; }
	method invoke(i: int) { }
}

class Internal extends Node {
	field shift: int;
	field mask: 32;
	field edges: EdgeList;

	constructor(s: int, m: 32, e: EdgeList) {
		shift = s;
		mask = m;
		edges = e;
	}

	method move(i: 32, error: Node): Node { 
		local val = (i >> shift) & mask;
		return edges.next(val :: int, error);
	}
}

class Leaf extends Node {
	field func: function(int);

	constructor(f: function(int)) { func = f; }
	method invoke(i: int) { func(i); }
}

class Recognizer {

	field root: Node;
	field error: Node;

	constructor(r: Node, e: Node) {
		root = r;
		error = e;
	}

	method decode(i: 32) {
		local pos = root;
		while ( true ) {
			if ( pos <: Leaf ) break;
			pos = pos.move(i, error);
		}
		pos.invoke(i :: int);
	}
}
