// @Harness: v2-seman
// @Test: typechecking > raw types > bit access operations
// @Result: PASS

class raw_index04 {
    method m(a: 8, i: int) {
	a[i] = 0b1;
    }
}
