// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=0, 2=0, 3=0, 4=0, 5=0, 6=42

component default04a {
    field foo: int[] = new int[4];
    field bar: int = foo[0];

    method main(arg: int): int {
	if ( arg == 1 ) return foo[0];
	if ( arg == 2 ) return foo[1];
	if ( arg == 3 ) return foo[2];
	if ( arg == 4 ) return foo[3];
	if ( arg == 5 ) return bar;
	return 42; 
    }
}
