// @Harness: v2-seman
// @Result: CannotInferTypeParam @ 9:18

component mp_delegate01 {
    method makeArray<X>(a: X): X[] {
	return new X[0];	
    }
    method test() {
	local x = makeArray;
    }
}
