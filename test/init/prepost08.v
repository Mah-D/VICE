// @Harness: v2-init
// @Test: pre/post increment options
// @Result: PASS

class prepost08_obj {
    field foo: int = 1;
    field bar: int = foo--;
}

component prepost08 {
    field foo: prepost08_obj = new prepost08_obj();
}

/* 
heap {
    record #0:1:prepost08 {
        field foo: prepost08_obj = #1:prepost08_obj;
    }
    record #1:2:prepost08_obj {
        field foo: int = int:0;
        field bar: int = int:1;
    }
}*/
