// @Harness: v2-seman
// @Result: PASS

component mp_delegate03 {
    method makeArray<X>(a: X): X[] {
	return new X[0];	
    }
    method test(): int[] {
	local x: function(int): int[] = makeArray;
	return x(0);
    }
}
