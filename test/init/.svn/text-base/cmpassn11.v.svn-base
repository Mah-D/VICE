// @Harness: v2-init
// @Test: pre/post increment operations
// @Result: PASS

class cmpassn11_obj {
    field foo: 16 = foo = 0x4;
    field bar: 16 = foo ^= 0x6;
}

component cmpassn11 {
    field foo: cmpassn11_obj = new cmpassn11_obj();
}

/* 
heap {
    record #0:1:cmpassn11 {
        field foo: cmpassn11_obj = #1:cmpassn11_obj;
    }
    record #1:2:cmpassn11_obj {
        field foo: int = raw.16:0x2;
        field bar: int = raw.16:0x2;
    }
}*/
