// @Harness: v2-init
// @Test: pre/post increment operations
// @Result: PASS

class cmpassn09_obj {
    field foo: 16 = 0x6;
    field bar: 16 = foo &= 0x5;
}

component cmpassn09 {
    field foo: cmpassn09_obj = new cmpassn09_obj();
}

/* 
heap {
    record #0:1:cmpassn09 {
        field foo: cmpassn09_obj = #1:cmpassn09_obj;
    }
    record #1:2:cmpassn09_obj {
        field foo: int = raw.16:0x4;
	field bar: int = raw.16:0x4;
    }
}*/
