// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

component switch10 {
    field foo: int = ds(10);

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

/* 
heap {
    record #0:1:switch10 {
        field foo: int = int:-1;
    }
} */
