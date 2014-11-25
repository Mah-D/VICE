// @Harness: v2-init
// @Test: pre/post increment options
// @Result: PASS

class prepost10_obj {
    field foo: int = 6;
    field bar: int = --foo;
}

component prepost10 {
    field foo: prepost10_obj = new prepost10_obj();

}

/* 
heap {
    record #0:1:prepost10 {
        field foo: prepost10_obj = #1:prepost10_obj;
    }
    record #1:2:prepost10_obj {
        field foo: int = int:5;
        field bar: int = int:5;
    }
}*/
