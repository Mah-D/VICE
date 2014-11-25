// @Harness: v2-seman
// @Test: typechecking; int type
// @Result: PASS

class int_auto01 {
    method check(a: int, b: int): int {
	local x: int = a;
	x++;
	x--;
	++x;
	--x;
	return x;
    }
}
