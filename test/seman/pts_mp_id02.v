// @Harness: v2-seman
// @Result: PASS

component mp_id02 {
    method test() {
	local x: int = id(0);
    }
    method id<X>(x: X): X {
	return x;	
    }
}
