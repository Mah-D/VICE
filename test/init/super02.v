// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

class super02_a {
    field foo: int = 1;
    field joe: int;
    constructor() {
        joe = 2;
    }
}

class super02_b extends super02_a {
    field bar: int = foo + joe;
}

component super02 {
    field baz: super02_a = new super02_b();
}

/* 
heap {
    record #0:1:super02 {
        field baz: super02_a = #1:super02_b;
    }
    record #1:3:super02_b {
        field foo: int = int:1;
        field joe: int = int:2;
        field bar: int = int:3;
    }
} */
