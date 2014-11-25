// @Harness: v2-seman
// @Result: PASS

component mp_id10 {
    method test() {
	local x = new int[128];
	x[id('\n')] = 0;
    }
    method id<X>(x: X): X {
	return x;	
    }
}
