// @Harness: v2-seman
// @Result: PASS

component mp_unify06 {
    method test() {
	local x: int[] = id(new int[5], null);
	local y: char[] = id(new char[5], null);
	local z: 32[] = id(new 32[5], null);
	local w: 8[] = id(new 8[5], null);
    }
    method id<X>(x: X, y: X): X {
	return x;
    }
}
