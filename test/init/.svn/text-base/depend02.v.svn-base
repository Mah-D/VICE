// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

component depend02a {
    field foo: int = depend02b.bar;
}

component depend02b {
    field bar: int;
    constructor() {
	bar = 42;
    }
}

/* 
heap {
    record #0:1:depend02a {
        field foo: int = int:42;
    }
    record #1:1:depend02b {
	field bar: int = int:42;
    }
} */
