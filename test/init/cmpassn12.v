// @Harness: v2-init
// @Test: pre/post increment operations
// @Result: PASS

class cmpassn12_obj {
    field foo: int = 13;
    field bar: int = foo %= 7;
}

component cmpassn12 {
    field foo: cmpassn12_obj = new cmpassn12_obj();
}

/* 
heap {
    record #0:1:cmpassn12 {
        field foo: cmpassn12_obj = #1:cmpassn12_obj;
    }
    record #1:2:cmpassn12_obj {
        field foo: int = int:6;
        field bar: int = int:6;
    }
}*/
