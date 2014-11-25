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
}

component list_init01 {
    field x: List<int> = makeList(5, 8);

    method makeList<T>(a: T, b: T): List<T> {
	local list = new List<T>();
	list.add(a);
	list.add(b);
	return list;
    }
}

/*
heap {
    record #0:1:list_init01 {
        field x: List<int> = #1:List<int>;
    }
    record #1:1:List<int> {
        field head: Node<int> = #3:Node<int>;
    }
    record #2:1:Node<int> {
        field value: int = int:5;
        field link: Node<int> = #null;
    }
    record #3:1:Node<int> {
        field value: int = int:8;
        field link: Node<int> = #2:Node<int>;
    }
}
*/
