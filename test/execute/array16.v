// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=104, 2=105, 3=99, 4=104, 5=101, 6=108, 7=108, 8=111, 9=42

component array16 {
    field a: char[][] =  { "hi", {'c'}, "hello" };

    method getf(f: char[], i: int): char {
	return f[i];
    }

    method main(arg: int): char {
	if ( arg == 1 ) return getf(a[0], 0);
	if ( arg == 2 ) return getf(a[0], 1);
	if ( arg == 3 ) return getf(a[1], 0);
	if ( arg == 4 ) return getf(a[2], 0);
	if ( arg == 5 ) return getf(a[2], 1);
	if ( arg == 6 ) return getf(a[2], 2);
	if ( arg == 7 ) return getf(a[2], 3);
	if ( arg == 8 ) return getf(a[2], 4);
	return '*';
    }
}
