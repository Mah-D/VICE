// @Harness: v2-init
// @Test: pre/post increment operations
// @Result: PASS

class cmpassn24_obj {
    field x: 16 = 0x1984;
    field y: 16 = 0x2001;
    constructor() {
	y = (x ^= (y ^= x)) ^ y;
    }
}

component cmpassn24 {
    field foo: cmpassn24_obj = new cmpassn24_obj();
}

/* 
heap {
    record #0:1:cmpassn24 {
        field foo: cmpassn24_obj = #1:cmpassn24_obj;
    }
    record #1:2:cmpassn24_obj {
        field x: int = raw.16:0x2001;
        field y: int = raw.16:0x1984;
    }
}*/
