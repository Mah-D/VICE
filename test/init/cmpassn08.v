// @Harness: v2-init
// @Test: pre/post increment options
// @Result: PASS

class cmpassn08_obj {
    field foo: int = 4;
    field bar: int = foo -= 3;
}

component cmpassn08 {
    field foo: cmpassn08_obj = new cmpassn08_obj();
}

/* 
heap {
    record #0:1:cmpassn08 {
        field foo: cmpassn08_obj = #1:cmpassn08_obj;
    }
    record #1:2:cmpassn08_obj {
        field foo: int = int:1;
        field bar: int = int:1;
    }
}*/
