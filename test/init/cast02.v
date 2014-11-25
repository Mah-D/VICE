// @Harness: v2-init
// @Test: TypeCast operator
// @Result: PASS

class cast02_obj {
}

component cast02 {
    field foo: cast02_obj = new cast02_obj();
    field bar: cast02_obj = foo :: cast02_obj;
}

/* 
heap {
    record #0:2:cast02 {
        field foo: cast02_obj = #1:cast02_obj;
        field bar: cast02_obj = #1:cast02_obj;
    }
    record #1:0:cast02_obj {
    }
}*/
