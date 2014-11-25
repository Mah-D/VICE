// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

class field03b_obj {
    field bar: int = baz();
    method baz(): int { return 1; }
}

component field03b {
    field foo: field03b_obj = new field03b_obj();
}

/* 
heap {
    record #0:1:field03b {
        field foo: field03b_obj = #1:field03b_obj;
    }
    record #1:1:field03b_obj {
        field bar: int = int:1;
    }
} */
