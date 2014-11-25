// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

component depend01a {
    field foo: int = depend01b.bar;
}

component depend01b {
    field bar: int = 42;
}

/* 
heap {
    record #0:1:depend01a {
        field foo: int = int:42;
    }
    record #1:1:depend01b {
	field bar: int = int:42;
    }
} */
