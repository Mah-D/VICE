// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

class field04b_obj {
    field bar: bool = true;
}

component field04b {
    field foo: field04b_obj = new field04b_obj();
}

/* 
heap {
    record #0:1:field04b {
        field foo: field04b_obj = #1:field04b_obj;
    }
    record #1:1:field04b_obj {
        field bar: bool = bool:true;
    }
} */
