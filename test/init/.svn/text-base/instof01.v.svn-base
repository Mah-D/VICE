// @Harness: v2-init
// @Test: operation of instanceof construct
// @Result: PASS

class instof01_obj {
}

component instof01 {
    field foo: instof01_obj;
    field bar: bool = foo instanceof instof01_obj;
}

/* 
heap {
    record #0:2:instof01 {
        field foo: instof01_obj = #null;
        field bar: bool = bool:false;
    }
}*/
