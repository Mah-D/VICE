// @Harness: v2-exec
// @Test: shift left operator, boundary cases
// @Result: 0=0, 1=1, 0=0

component raw_shlx01 {
    field res_01: 32 = op(64, 32);

    method op(a: 32, b: int): 32 {
	return a << b;
    }

    method main(arg: int): bool {
	if ( arg == 1 )  return op(64, 32) == res_01;
	return false;
    }
}
