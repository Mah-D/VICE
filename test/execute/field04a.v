// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=0, 1=1, 2=0, 3=0

component field04a {
    field a: bool = true;
    field b: bool = false;

    method main(arg: int): bool {
	if ( arg == 1 ) return a;
	if ( arg == 2 ) return b;
	return false;
    }
}
