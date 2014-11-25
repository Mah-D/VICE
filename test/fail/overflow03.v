// @Harness: v2-exec
// @Test: arithmetic operators
// @Result: 0=0, 1=0, 2=0, 3=0, 4=1, 5=0, 6=0, 7=0, 8=1, 9=0

component overflow03 {

    field f: int = 2147483647;
    field g: int = -2147483648;

    method main(arg: int): bool {
	if ( arg == 1 ) return (f + 1) > 0;
	if ( arg == 2 ) return (f + 1) > f;
	if ( arg == 3 ) return (g + 1) > 0;
	if ( arg == 4 ) return (g + 1) > g;
	if ( arg == 5 ) return (f + f) > 0;
	if ( arg == 6 ) return (f + f) > f;
	if ( arg == 7 ) return (g + g) > 0;
	if ( arg == 8 ) return (g + g) > g;
	return false;
    }
}
