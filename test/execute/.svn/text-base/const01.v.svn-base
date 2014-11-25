// @Harness: v2-exec
// @Test: compile-time constants for primitive types
// @Result: 0=0, 1=1, 2=1, 3=1, 4=1, 5=1, 6=0

component const01 {
    field a: int = 1;
    field b: int = -1;
    field c: int = 65536;
    field d: int = -200000;
    field e: int = -2147483648;

    method main(arg: int): bool {
	if ( arg == 1 ) return a == 1;
	if ( arg == 2 ) return b == -1;
	if ( arg == 3 ) return c == 65536;
	if ( arg == 4 ) return d == -200000;
	if ( arg == 5 ) return e == -2147483648;
	return false;
    }
}
