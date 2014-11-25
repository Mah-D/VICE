// @Harness: v2-exec
// @Test: array rtex_index exceptions
// @Result: 0=42, 1=BoundsCheckException, 2=BoundsCheckException, 3=BoundsCheckException, 4=BoundsCheckException, 5=BoundsCheckException, 6=BoundsCheckException, 7=BoundsCheckException, 8=BoundsCheckException, 9=42

component rtex_index08 {
    field a: 3[] = { 0b0 };
    field b: 8[] = { 0b0 };
    field c: 11[] = { 0b0 };
    field d: 16[] = { 0b0 };
    field e: 21[] = { 0b0 };
    field f: 32[] = { 0b0 };
    field g: 47[] = { 0b0 };
    field h: 64[] = { 0b0 };

    method main(arg: int): int {
	if ( arg == 1 ) return a[1] :: int;
	if ( arg == 2 ) return b[1] :: int;
	if ( arg == 3 ) return c[1] :: int;
	if ( arg == 4 ) return d[1] :: int;
	if ( arg == 5 ) return e[1] :: int;
	if ( arg == 6 ) return f[1] :: int;
	if ( arg == 7 ) return (g[1] & 0xff) :: int;
	if ( arg == 8 ) return (h[1] & 0xff) :: int;
	return 42;
    }
}
