// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=0, 2=0, 3=42

component default06a {
    field foo: char;
    field bar: char = foo;

    method main(arg: int): char {
	if ( arg == 1 ) return foo;
	if ( arg == 2 ) return bar;
	return '*';
    }
}
