// @Harness: v2-seman
// @Test: typechecking; int operations
// @Result: PASS

class raw_op03 {
    method check(a: 32, b: 32, c: int): 32 {
	local x: 32;
	x = a;
	x = a & b;
	x = a | b;
	x = a ^ b;
	x = a << c;
	x = a >> c;
	x = ~a;
	return x;
    }
}
