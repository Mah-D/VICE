// @Harness: v2-seman
// @Result: PASS

component fold03 {
    field a: int;
    field b: int;

    constructor() {
        local array = new int[1000];
	a = fold(add, array, array.length - 1);
        b = fold2(mult, array, array.length - 1);
    }

    method add(a: int, b: int): int {
	return a + b;
    }

    method mult(a: int, b: int): int {
        return a * b;
    }

    // iterative version of fold
    method fold(f: function(int, int): int, a: int[], m: int): int {
	return m == 0 ? a[0] : f(fold(f, a, m-1), a[m]);        
    }

    method fold2<T>(f: function(T, T): T, a: T[], m: int): T {
	if ( m == 0 ) return a[0];
	local p: T = fold2(f, a, m-1);
	local r: T = f(p, a[m]);
	return r;
    }
}
