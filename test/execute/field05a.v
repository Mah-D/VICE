// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=104, 2=101, 3=108, 4=108, 5=111, 6=42

component field05a {
    field a: char[] = "hello";

    method main(arg: int): char {
	if ( arg == 1 ) return a[0];
	if ( arg == 2 ) return a[1];
	if ( arg == 3 ) return a[2];
	if ( arg == 4 ) return a[3];
	if ( arg == 5 ) return a[4];
	return '*';
    }
}
