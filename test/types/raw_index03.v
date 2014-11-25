// @Harness: v2-seman
// @Test: typechecking > raw types > bit access operations
// @Result: PASS

class raw_index03 {
    method m(a: 8, b: 1) {
	a[0] = b;
    }
}
