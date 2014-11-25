// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=0, 2=0, 3=0, 4=0, 5=0, 6=42

component default05a {
    field foo: char[] = new char[4];
    field bar: char = foo[0];

    method main(arg: int): char {
	if ( arg == 1 ) return foo[0];
	if ( arg == 2 ) return foo[0];
	if ( arg == 3 ) return foo[0];
	if ( arg == 4 ) return foo[0];
	if ( arg == 5 ) return bar;
	return '*';
    }
}
