// @Harness: v2-seman
// @Test: typechecking; int type
// @Result: PASS

class int_char05 {
    method test(c: char): int {
	local x = new int[128];
	return x[c];
    }
}
