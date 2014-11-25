// @Harness: v2-seman
// @Test: typechecking; int operations
// @Result: PASS

class raw_op04 {
    method check(a: 64, b: 64, c: int): 64 {
	local x: 64;
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
