// @Harness: v2-init
// @Test: static method invocations
// @Result: PASS

component fold_init01 {
    field a: int;
    field b: int;

    constructor() {
        local array = { 1, 2, 3, 4, 5 };
	a = fold(add, array, array.length - 1);
        b = fold(mult, array, array.length - 1);
    }

    method add(a: int, b: int): int {
	return a + b;
    }

    method mult(a: int, b: int): int {
        return a * b;
    }

    method fold<T>(f: function(T, T): T, a: T[], m: int): T {
	return m == 0 ? a[0] : f(fold(f, a, m-1), a[m]);        
    }
}

/* 
heap {
    record #0:2:fold_init01 {
        field a: int = int:15;
        field b: int = int:120;
    }
} 
*/
