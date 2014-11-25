// @Harness: v2-seman
// @Result: PASS

component mp_id13 {
    method test() {
	local x = new int[8];
	x[id('0')] = 0;
    }
    method id<X>(x: X): X {
	return x;	
    }
}
