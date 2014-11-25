// @Harness: v2-seman
// @Result: PASS

component mp_unify11 {

    method test() {
	local a: int[] = null;
	local b: bool[] = null;
	local x = id(a, 0);
	local y = id(b, false);
    }
    method id<X>(y: X[], x: X): X {
	return x;	
    }
}
