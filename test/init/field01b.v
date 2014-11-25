// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

class field01b_obj {
    field bar: int = 1;
}

component field01b {
    field foo: field01b_obj = new field01b_obj();
}

/* 
heap {
    record #0:1:field01b {
        field foo: field01b_obj = #1:field01b_obj;
    }
    record #1:1:field01b_obj {
        field bar: int = int:1;
    }
} */
