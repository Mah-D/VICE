// @Harness: v2-seman
// @Result: PASS

component mp_unify11 {

    method test() {
	local a: int[] = null;
	local b: bool[] = null;
	local x = id(0, a);
	local y = id(true, b);
    }
    method id<X>(x: X, y: X[]): X {
	return x;	
    }
}
