// @Harness: v2-seman
// @Test: typechecking; int operations
// @Result: PASS

class raw_op01 {
    method check(a: 8, b: 8, c: int): 8 {
	local x: 8;

	// basic operators
	x = a;
	x = a & b;
	x = a | b;
	x = a ^ b;
	x = a << c;
	x = a >> c;
	x = ~a;

	// indexing operators
	local y: 1;
	y = a[c];
	a[c] = b[c];	

	// concat operator
	local z: 16;
	z = a # b;

	return x;
    }
}
