// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

class super04_a {
    field foo: int = 1;
    field joe: int;
    constructor() {
        joe = 2;
    }
}

class super04_b extends super04_a {
    field bar: int = foo + joe;
    field john: int;
    constructor() : super() {
        john = bar + 1;
    }
}

component super04 {
    field baz: super04_a = new super04_b();
}

/* 
heap {
    record #0:1:super04 {
        field baz: super04_a = #1:super04_b;
    }
    record #1:4:super04_b {
        field foo: int = int:1;
        field joe: int = int:2;
        field bar: int = int:3;
        field john: int = int:4;
    }
} */
