// @Harness: v2-seman
// @Result: CannotInferTypeParam @ 9:18

component mp_delegate02 {
    method makeArray<X>(a: X): X[] {
	return new X[0];	
    }
    method test(): int[] {
	local x = makeArray;
	return x(0);
    }
}
