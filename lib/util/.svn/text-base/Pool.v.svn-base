class PItem<T> {
	field item: T;
	field next: PItem<T>;
}

class Pool<T> {
	field free: PItem<T>;
	field used: PItem<T>;

	constructor(af: function(): T, sz: int) {
		while ( sz-- > 0 ) {
			local n = new PItem<T>();
			n.next = free;
			n.item = af();
			free = n;
		}
	}

	method acquire(): T {
		local n = free;
		free = free.next;
		n.next = used;
		used = n;
		return n.item;
	}

	method release(t: T) {
		local n = used;
		used = used.next;
		n.next = free;
		free = n;
		n.item = t;
	}
}