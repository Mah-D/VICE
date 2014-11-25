// @Harness: v2-init
// @Test: pre/post increment operations
// @Result: PASS

class prepost12_obj {
    field foo: int = ++foo;
}

component prepost12 {
    field foo: prepost12_obj = new prepost12_obj();
}

/* 
heap {
    record #0:1:prepost12 {
        field foo: prepost12_obj = #1:prepost12_obj;
    }
    record #1:1:prepost12_obj {
        field foo: int = int:1;
    }
}*/
