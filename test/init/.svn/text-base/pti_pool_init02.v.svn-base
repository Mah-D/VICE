// @Harness: v2-init
// @Test: static method invocations
// @Result: PASS

class E {
}

class Pool<T> {
    field array: T[];
    field none: T;
    field head: int;
    field used: int;

    constructor(size: int, nf: function(): T, n: T) {
	array = new T[size];
	local i: int;
	for ( i = 0; i < size; i++ ) array[i] = nf();
	head = 0;
	used = 0;
	none = n;
    }

    method acquire(): T {
	if ( used == array.length ) return none;
	local x = array[head];
	head = (head + 1) % array.length;
	used++;
	return x;
    }

    method release(x: T) {
	if ( used == 0 ) return;
	local pos = (head - used) % array.length;
	array[pos] = x;
	used--;
    }
}

component pool_init02 {
    field a: Pool<E> = new Pool<E>(3, newE, null);
    field b: E = a.acquire();

    method newE(): E {
	return new E();
    }
}

/* 
heap {
    record #0:2:pool_init02 {
        field a: Pool<E> = #1:Pool<E>;
	field b: E = #3:E;
    }
    record #1:3:Pool<E> {
	field array: E[] = #2:E[3];
	field none: E = #null;
	field head: int = int:1
	field used: int = int:1;
    }
    record #2:3:E[3] {
	field 0: E = #3:E;
	field 1: E = #4:E;
	field 2: E = #5:E;
    }
    record #3:0:E {
    }
    record #4:0:E {
    }
    record #5:0:E {
    }
} 
*/
