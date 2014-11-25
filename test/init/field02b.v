// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

class field02b_obj {
    field bar: int;
    constructor() {
        bar = 1;
    }
}

component field02b {
    field foo: field02b_obj = new field02b_obj();
}

/* 
heap {
    record #0:1:field02b {
        field foo: field02b_obj = #1:field02b_obj;
    }
    record #1:1:field02b_obj {
        field bar: int = int:1;
    }
} */
