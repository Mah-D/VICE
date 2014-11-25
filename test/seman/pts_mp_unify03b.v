// @Harness: v2-seman
// @Result: PASS

component mp_unify03 {
    method test() {
	local x: 32, y: 32;
	x = id(0, 0b00);
	y = id(0b00, 0);
    }
    method id<X>(x: X, y: X): X {
	return x;	
    }
}
