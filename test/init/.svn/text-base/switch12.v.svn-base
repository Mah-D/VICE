// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

component switch12 {
    field foo: int;

    constructor() {
	ds(1);
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

/* 
heap {
    record #0:1:switch12 {
        field foo: int = int:11;
    }
} */
