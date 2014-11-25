// @Harness: v2-seman
// @Result: ExpectedVarType @ 6:23

component mp_id03 {
    method test() {
	local x = id(null);
    }
    method id<X>(x: X): X {
	return x;	
    }
}
