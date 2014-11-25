// @Harness: v2-seman
// @Test: typechecking; int type
// @Result: PASS

class int_char04 {
    method test(c: char) {
	local x: int = c;
    }
}
