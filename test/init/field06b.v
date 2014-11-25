// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

class field06b_obj {
    field bar: char = 'h';
}

component field06b {
    field foo: field06b_obj = new field06b_obj();
}

/* 
heap {
    record #0:1:field06b {
        field foo: field06b_obj = #1:field06b_obj;
    }
    record #1:1:field06b_obj {
        field bar: char = char:104;
    }
} */
