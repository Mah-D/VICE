// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=0, 2=0, 3=0, 4=0, 5=0, 6=0, 7=42

component array09 {
    field a: char[] =  new char[1];
    field b: char[] =  new char[2];
    field c: char[] =  new char[3];

    method main(arg: int): char {
	if ( arg == 1 ) return a[0];
	if ( arg == 2 ) return b[0];
	if ( arg == 3 ) return b[1];
	if ( arg == 4 ) return c[0];
	if ( arg == 5 ) return c[1];
	if ( arg == 6 ) return c[2];
	return '*';
    }
}
