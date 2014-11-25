// @Harness: v2-exec
// @Test: arithmetic operators
// @Result: 0=0, 1=1, 2=1, 3=0

component scope01 {
    method main(arg: int): bool {
	{ local a = 1; if ( arg == a ) return true; }
	{ local a = 2; if ( arg == a ) return true; }
	return false;
    }
}
