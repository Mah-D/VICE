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

component list_init02 {
    field y: int;

    constructor() {
	makeList(5, 8).apply(add);
    }

    method makeList<T>(a: T, b: T): List<T> {
	local list = new List<T>();
	list.add(a);
	list.add(b);
	return list;
    }

    method add(v: int) { y += v; }
}

/*
heap {
    record #0:1:list_init02 {
        field y: int= int:13;
    }
}
*/
