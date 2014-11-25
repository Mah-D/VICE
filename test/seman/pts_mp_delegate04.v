// @Harness: v2-seman
// @Result: PASS

component mp_delegate04 {
    method makeArray<X>(a: X): X[] {
	return new X[0];	
    }
    method test(): int[] {
	local x: function(int): int[] = makeArray;
	return x(0);
    }
    method take<T>(f: function(T): T[], x: T): T[] {
	return f(x);
    }
}
