// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=10, 1=11, 2=13, 3=15, 4=15, 5=20, 6=255, 7=20, 8=20, 9=255

component switch02 {
    field foo: int;

    method main(arg: int): int {
	ds(arg);
	return foo;
    }

    method ds(v: int) {
	switch ( v ) {
	    case ( 0 ) foo = 10;
	    case ( 2 ) foo = 13;
	    case ( 5, 8, 7 ) foo = 20;
	    case ( 1 ) foo = 11;
	    case ( 3, 4 ) foo = 15;
	    default foo = -1;
        }
    }
}
