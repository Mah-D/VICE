// @Harness: v2-init
// @Test: TypeQuery operator
// @Result: PASS

class instof01b_obj {
}

component instof01b {
    field foo: instof01b_obj;
    field bar: bool = foo <: instof01b_obj;
}

/* 
heap {
    record #0:2:instof01b {
        field foo: instof01b_obj = #null;
        field bar: bool = bool:false;
    }
}*/
