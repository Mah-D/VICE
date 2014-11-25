// @Harness: v2-exec
// @Test: static method invocations
// @Result: 0=42, 1=15, 2=120, 3=31, 4=31, 5=42

component ptex_fold01 {

    field iarr: int[] = { 1, 2, 3, 4, 5 };
    field rarr: 5[] = { 0b00001, 0b10000, 0b01000, 0b00010, 0b00100 };

    method main(arg: int): int {
	if ( arg == 1 ) return fold(add, iarr, iarr.length - 1);
	if ( arg == 2 ) return fold(mult, iarr, iarr.length - 1);
        if ( arg == 3 ) return fold(ror, rarr, rarr.length - 1) :: int;
        if ( arg == 4 ) return fold(rxor, rarr, rarr.length - 1) :: int;
        return 42;
    }

    method add(a: int, b: int): int {
	return a + b;
    }

    method mult(a: int, b: int): int {
        return a * b;
    }

    method ror(a: 5, b: 5): 5 {
        return a | b;
    }

    method rxor(a: 5, b: 5): 5 {
        return a ^ b;
    }

    method fold<T>(f: function(T, T): T, a: T[], m: int): T {
	return m == 0 ? a[0] : f(fold(f, a, m-1), a[m]);        
    }
}
