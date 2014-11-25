// @Harness: v2-init
// @Test: operation of instanceof construct
// @Result: PASS

class instof02_obj {
}

component instof02 {
    field foo: instof02_obj = new instof02_obj();
    field bar: bool = foo instanceof instof02_obj;
}

/* 
heap {
    record #0:2:instof02 {
        field foo: instof02_obj = #1:instof02_obj;
        field bar: bool = bool:true;
    }
    record #1:0:instof02_obj {
    }
}*/
