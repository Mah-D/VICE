// @Harness: v2-seman
// @Result: PASS

component mp_unify02 {
    method test() {
	local x: int, y: int;
	x = id(0, 'x');
	y = id('x', 0);
    }
    method id<X>(x: X, y: X): X {
	return x;
    }
}
