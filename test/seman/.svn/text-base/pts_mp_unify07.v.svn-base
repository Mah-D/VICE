// @Harness: v2-seman
// @Result: PASS

component mp_unify07 {
    method test() {
	local x: int[] = id(null, new int[5]);
	local y: char[] = id(null, new char[5]);
	local z: 32[] = id(null, new 32[5]);
	local w: 8[] = id(null, new 8[5]);
    }
    method id<X>(x: X, y: X): X {
	return x;
    }
}
