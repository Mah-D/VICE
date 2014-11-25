// @Harness: v2-init
// @Test: static method invocations
// @HeapGC: false
// @Result: PASS

component fold01 {
    field a: int;
    field b: int;

    constructor() {
        local array = { 1, 2, 3, 4, 5 };
	a = fold(add, array, 5);
        b = fold(mult, array, 5);
    }

    method add(a: int, b: int): int {
	return a + b;
    }

    method mult(a: int, b: int): int {
        return a * b;
    }

    // iterative version of fold
    method fold(f: function(int, int): int, a: int[], m: int): int {
        local cumul = a[0];
        local cntr = 1;
        for ( ; cntr < m; cntr++ ) cumul = f(cumul, a[cntr]);
	return cumul; 
    }
}

/* 
heap {
    record #0:2:fold01 {
        field a: int = int:15;
        field b: int = int:120;
    }
} */
