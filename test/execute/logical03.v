// @Harness: v2-exec
// @Test: logical operators
// @Result: 0=0, 1=0, 2=1, 3=0

component logical03 {

    method op(a: bool): bool {
	return !a;
    }

    method main(arg: int): bool {
	if ( arg == 1 ) return op(true);
	if ( arg == 2 ) return op(false);
	return false;
    }
}
