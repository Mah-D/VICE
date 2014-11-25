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
}

component cl_list01 {
    method test() {
	local x: List<int> = makeList(0, 0);
    }
    method makeList<T>(a: T, b: T): List<T> {
	local list = new List<T>();
	list.add(a);
	list.add(b);
	return list;
    }
}
