// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=10, 1=11, 2=12, 3=13, 4=15, 5=15, 6=255, 7=20, 8=20, 9=20, 10=255, 100=255

component switch01 {

    method main(arg: int): int {
	return ds(arg);
    }

    method ds(v: int): int {
	switch ( v ) {
	    case ( 0 ) return 10;
	    case ( 1 ) return 11;
	    case ( 2 ) return 12;
	    case ( 3 ) return 13;
	    case ( 4, 5 ) return 15;
	    case ( 7, 8, 9 ) return 20;
	    default return -1;
        }
    }
}
