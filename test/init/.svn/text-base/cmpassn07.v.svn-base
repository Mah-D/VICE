// @Harness: v2-init
// @Test: pre/post increment operations
// @Result: PASS

class cmpassn07_obj {
    field foo: int = 1;
    field bar: int = foo += 2;
}

component cmpassn07 {
    field foo: cmpassn07_obj = new cmpassn07_obj();
}

/* 
heap {
    record #0:1:cmpassn07 {
        field foo: cmpassn07_obj = #1:cmpassn07_obj;
    }
    record #1:2:cmpassn07_obj {
        field foo: int = int:3;
        field bar: int = int:3;
    }
}*/
