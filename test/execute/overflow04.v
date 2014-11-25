// @Harness: v2-exec
// @Test: arithmetic operators
// @Result: 0=0, 1=1, 2=1, 3=1, 4=0, 5=0

component overflow04 {

    field f: int = 2147483647;
    field g: int = -2147483648;

    method main(arg: int): bool {
	if ( arg == 1 ) return (f - 1) > 0;
	if ( arg == 2 ) return (f - 1) < f;
	if ( arg == 3 ) return (g - 1) > 0;
	if ( arg == 4 ) return (g - 1) == 0;
	return false;
    }
}
