// @Harness: v2-init
// @Test: operation of instanceof construct
// @Result: PASS

class cast01_obj {
}

component cast01 {
    field foo: cast01_obj;
    field bar: cast01_obj = foo :: cast01_obj;
}

/* 
heap {
    record #0:2:cast01 {
        field foo: cast01_obj = #null;
        field bar: cast01_obj = #null;
    }
}*/
