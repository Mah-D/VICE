// @Harness: v2-seman
// @Test: typechecking > raw types > bit access operations
// @Result: PASS

class raw_index06 {
    method m(a: 8, i: int, j: int) {
	local x: 1 = a[i] = a[j];
    }
}
