// @Harness: v2-exec
// @Test: compile-time constants for primitive types
// @Result: 0=0, 1=1, 2=0, 3=1, 4=1, 5=0, 6=0, 7=0

component const03 {
    field a: bool = true;
    field b: bool = false;

    method main(arg: int): bool {
	if ( arg == 1 ) return a;
	if ( arg == 2 ) return b;
	if ( arg == 3 ) return a == true;
	if ( arg == 4 ) return b == false;
	if ( arg == 5 ) return a == false;
	if ( arg == 6 ) return b == true;
	return false;
    }
}
