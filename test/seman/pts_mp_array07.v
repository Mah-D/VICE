// @Harness: v2-seman
// @Result: PASS

component mp_array07 {
    method test() {
	set0(0, null);
    }
    method set0<X>(b: X, a: X[]) {
	a[0] = b;	
    }
}
