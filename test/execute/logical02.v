// @Harness: v2-exec
// @Test: logical operators
// @Result: 0=0, 1=1, 2=0, 3=1, 4=1, 5=0

component logical02 {

    method op(a: bool, b: bool): bool {
	return a or b;
    }

    method main(arg: int): bool {
	if ( arg == 1 ) return op(true, true);
	if ( arg == 2 ) return op(false, false);
	if ( arg == 3 ) return op(true, false);
	if ( arg == 4 ) return op(false, true);
	return false;
    }
}
