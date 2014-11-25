// @Harness: v2-seman
// @Result: PASS

component mp_unify04 {
    method test() {
	local x: 8 = id('c', 0b00);
	local y: 8 = id(0b00, 'c');
    }
    method id<X>(x: X, y: X): X {
	return x;	
    }
}
