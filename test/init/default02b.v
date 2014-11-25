// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

class default02b_obj {
    field foo: bool;
    field bar: bool = !foo;
}

component default02b {
    field baz: default02b_obj = new default02b_obj();
}

/* 
heap {
    record #0:1:default02b {
        field baz: default02b_obj = #1:default02b_obj;
    }
    record #1:2:default02b_obj {
        field foo: bool = bool:false;
        field bar: bool = bool:true;
    }
} */
