// @Harness: v2-seman
// @Result: PASS

component mp_unify03 {
    method test() {
	local x: 32 = id(0, 0b00);
	local y: 32 = id(0b00, 0);
    }
    method id<X>(x: X, y: X): X {
	return x;	
    }
}
