// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

class default06b_obj {
    field foo: char;
    field bar: char = foo;
}

component default06b {
    field baz: default06b_obj = new default06b_obj();
}

/* 
heap {
    record #0:1:default06b {
        field baz: default06b_obj = #1:default06b_obj;
    }
    record #1:2:default06b_obj {
        field foo: char = char:0;
        field bar: char = char:0;
    }
} */
