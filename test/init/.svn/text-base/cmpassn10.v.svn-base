// @Harness: v2-init
// @Test: pre/post increment options
// @Result: PASS

class cmpassn10_obj {
    field foo: 16 = 0x6;
    field bar: 16 = foo |= 0x1;
}

component cmpassn10 {
    field foo: cmpassn10_obj = new cmpassn10_obj();

}

/* 
heap {
    record #0:1:cmpassn10 {
        field foo: cmpassn10_obj = #1:cmpassn10_obj;
    }
    record #1:2:cmpassn10_obj {
        field foo: int = raw.16:0x7;
        field bar: int = raw.16:0x7;
    }
}*/
