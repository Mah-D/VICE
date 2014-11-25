// @Harness: v2-seman
// @Result: PASS

component mp_id07 {
    method test() {
	while ( id(true) ) ;
    }
    method id<X>(x: X): X {
	return x;	
    }
}
