// @Harness: v2-init
// @Result: PASS

class Node<X> {
    field value: X;
    field link: Node<X>;
}

class List<X> {
    field head: Node<X>;

    method add(x: X) {
	local nn = new Node<X>();
	nn.value = x;
	nn.link = head;
	head = nn;	
    }

    method apply(f: function(X)) {
	local pos = head;
	while ( head != null ) {
	    f(head.value);
	    head = head.link;
	}
    }
}

component list_init03 {
    field y: int;

    constructor() {
	local v = { 0, 12, 13, -9, 5 };
	toList(v).apply(add);
    }

    method toList<T>(a: T[]): List<T> {
	local list = new List<T>();
	local i: int;
	for ( i = 0; i < a.length; i++) list.add(a[i]);
	return list;
    }

    method add(v: int) { y += v; }
}

/*
heap {
    record #0:1:list_init03 {
        field y: int = int:21;
    }
}
*/
