// @Harness: v2-seman
// @Test: typechecking > raw types > bit access operations
// @Result: PASS

class raw_index02 {
    method m(a: 8) {
	a[0] = 0b1;
    }
}
