// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

class super05_a {
    field foo: int = 1;
    field joe: int;
    constructor(j: int) {
        joe = j;
    }
}

class super05_b extends super05_a {
    field bar: int = foo + joe;
    field john: int;
    constructor() : super(2) {
        john = bar + 1;
    }
}

component super05 {
    field baz: super05_a = new super05_b();
}

/* 
heap {
    record #0:1:super05 {
        field baz: super05_a = #1:super05_b;
    }
    record #1:4:super05_b {
        field foo: int = int:1;
        field joe: int = int:2;
        field bar: int = int:3;
        field john: int = int:4;
    }
} */
