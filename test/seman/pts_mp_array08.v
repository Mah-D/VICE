// @Harness: v2-seman
// @Result: PASS

component mp_array08 {
    method test() {
	local x: int[] = makeArray();
    }
    method makeArray<X>(): X[] {
	return new X[0];	
    }
}
