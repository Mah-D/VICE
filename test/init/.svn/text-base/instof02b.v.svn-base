// @Harness: v2-init
// @Test: TypeQuery operator
// @Result: PASS

class instof02b_obj {
}

component instof02b {
    field foo: instof02b_obj = new instof02b_obj();
    field bar: bool = foo <: instof02b_obj;
}

/* 
heap {
    record #0:2:instof02b {
        field foo: instof02b_obj = #1:instof02b_obj;
        field bar: bool = bool:true;
    }
    record #1:0:instof02b_obj {
    }
}*/
