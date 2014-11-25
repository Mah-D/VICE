// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

class super06_a {
    field foo: int = 1;
    field joe: int;
    constructor(j: int) {
        joe = j;
    }
}

class super06_b extends super06_a {
    field bar: int = foo + joe;
    field john: int;
    constructor(k: int) : super(2) {
        john = bar + k;
    }
}

component super06 {
    field baz: super06_a = new super06_b(1);
}

/* 
heap {
    record #0:1:super06 {
        field baz: super06_a = #1:super06_b;
    }
    record #1:4:super06_b {
        field foo: int = int:1;
        field joe: int = int:2;
        field bar: int = int:3;
        field john: int = int:4;
    }
} */
