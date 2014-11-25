// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

class super03_a {
    field foo: int = 1;
    field joe: int;
    constructor() {
        joe = 2;
    }
}

class super03_b extends super03_a {
    field bar: int = foo + joe;
    field john: int;
    constructor() {
        john = bar + 1;
    }
}

component super03 {
    field baz: super03_a = new super03_b();
}

/* 
heap {
    record #0:1:super03 {
        field baz: super03_a = #1:super03_b;
    }
    record #1:4:super03_b {
        field foo: int = int:1;
        field joe: int = int:2;
        field bar: int = int:3;
        field john: int = int:4;
    }
} */
