// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

class super01_a {
    field foo: int = 1;
}

class super01_b extends super01_a {
    field bar: int = foo + 1;
}

component super01 {
    field baz: super01_a = new super01_b();
}

/* 
heap {
    record #0:1:super01 {
        field baz: super01_a = #1:super01_b;
    }
    record #1:2:super01_b {
        field foo: int = int:1;
        field bar: int = int:2;
    }
} */
