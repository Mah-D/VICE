// @Harness: v2-init
// @Test: static method invocations
// @HeapGC: false
// @Result: PASS

component fold02 {
    field a: int;
    field b: int;

    constructor() {
        local array = { 1, 2, 3, 4, 5 };
	a = fold(add, array, 0, 4);
        b = fold(mult, array, 0, 4);
    }

    method add(a: int, b: int): int {
	return a + b;
    }

    method mult(a: int, b: int): int {
        return a * b;
    }

    // iterative version of fold
    method fold(f: function(int, int): int, a: int[], s: int, m: int): int {
	if ( s == m ) return a[s];
	else return f(fold(f, a, s, m-1), a[m]);        
    }
}

/* 
heap {
    record #0:2:fold02 {
        field a: int = int:15;
        field b: int = int:120;
    }
} */
