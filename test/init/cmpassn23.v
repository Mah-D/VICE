// @Harness: v2-init
// @Test: pre/post increment operations
// @Result: PASS

class cmpassn23_obj {
    field x: 16 = 0x1984;
    field y: 16 = 0x2001;
    constructor() {
	x ^= y ^= x ^= y;
    }
}

component cmpassn23 {
    field foo: cmpassn23_obj = new cmpassn23_obj();
}

/* 
heap {
    record #0:1:cmpassn23 {
        field foo: cmpassn23_obj = #1:cmpassn23_obj;
    }
    record #1:2:cmpassn23_obj {
        field x: int = raw.16:0x0000;
        field y: int = raw.16:0x1984;
    }
}*/
