// @Harness: v2-seman
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
	while ( pos != null ) {
	    f(pos.value);
	    pos = pos.link;
	}
    }
}

component cl_list02 {
    method test() {
	local x: List<int> = makeList(0, 0);
	x.apply(print);
    }
    method makeList<T>(a: T, b: T): List<T> {
	local list = new List<T>();
	list.add(a);
	list.add(b);
	return list;
    }
    method print(i: int) {
    }
}
