// @Harness: v2-exec
// @Test: arithmetic operators
// @Result: 0=0, 1=1, 2=1, 3=1, 4=1, 5=1, 6=1, 7=1, 8=1, 9=0

component overflow01 {

    field f: int = 127;
    field g: int = 255;

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
