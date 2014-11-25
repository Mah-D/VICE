// @Harness: v2-exec
// @Test: arithmetic operators
// @Result: 0=0, 1=1, 2=0

component raw_shrx01 {

    field res_01: 32 = op(0xaa, 32);

    method op(a: 32, b: int): 32 {
	return a >> b;
    }

    method main(arg: int): bool {
	if ( arg == 1 ) return op(0xaa, 32) == res_01;
	return false;
    }
}
