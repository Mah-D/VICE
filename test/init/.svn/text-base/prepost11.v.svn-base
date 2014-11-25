// @Harness: v2-init
// @Test: pre/post increment operations
// @Result: PASS

class prepost11_obj {
    field foo: int = foo++;
}

component prepost11 {
    field foo: prepost11_obj = new prepost11_obj();
}

/* 
heap {
    record #0:1:prepost11 {
        field foo: prepost11_obj = #1:prepost11_obj;
    }
    record #1:1:prepost11_obj {
        field foo: int = int:0;
    }
}*/
