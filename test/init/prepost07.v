// @Harness: v2-init
// @Test: pre/post increment operations
// @Result: PASS

class prepost07_obj {
    field foo: int = 1;
    field bar: int = foo++;
}

component prepost07 {
    field foo: prepost07_obj = new prepost07_obj();
}

/* 
heap {
    record #0:1:prepost07 {
        field foo: prepost07_obj = #1:prepost07_obj;
    }
    record #1:2:prepost07_obj {
        field foo: int = int:2;
        field bar: int = int:1;
    }
}*/
