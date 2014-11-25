// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

component depend03a {
    field foo: int = depend03b.bar;
    field baz: int = 47;
}

component depend03b {
    field bar: int;
    field duk: int;
    constructor() {
	bar = 42;
	duk = depend03a.baz;
    }
}

/* 
heap {
    record #0:1:depend03a {
        field foo: int = int:42;
	field baz: int = int:47;
    }
    record #1:1:depend03b {
	field bar: int = int:42;
	field duk: int = int:0;
    }
} */
