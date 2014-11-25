// @Harness: v2-exec
// @Test: compile-time constants for primitive types
// @Result: 0=0, 1=1, 2=1, 3=1, 4=1, 5=1, 6=1, 7=1, 8=1, 9=0

component const05 {
    field a: int = 1;
    field b: int = -1;
    field c: int = 0b0101 :: int;
    field d: int = 0xff :: int;
    field e: int = 0777 :: int;
    field f: int = 65536;
    field g: int = -200000;
    field h: int = -2147483648;

    method main(arg: int): bool {
	if ( arg == 1 ) return a == 1;
	if ( arg == 2 ) return b == -1;
	if ( arg == 3 ) return c == 5;
	if ( arg == 4 ) return d == 255;
	if ( arg == 5 ) return e == 511;
	if ( arg == 6 ) return f == 65536;
	if ( arg == 7 ) return g == -200000;
	if ( arg == 8 ) return h == -2147483648;
	return false;
    }
}
