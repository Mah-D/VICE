// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

class default01b_obj {
    field foo: int;
    field bar: int = foo + 1;
}

component default01b {
    field baz: default01b_obj = new default01b_obj();
}

/* 
heap {
    record #0:1:default01b {
        field baz: default01b_obj = #1:default01b_obj;
    }
    record #1:2:default01b_obj {
        field foo: int = int:0;
        field bar: int = int:1;
    }
} */
