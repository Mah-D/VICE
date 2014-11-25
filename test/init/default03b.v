// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

class default03b_obj {
    field foo: int[];
    field bar: bool = foo == null;
}

component default03b {
    field baz: default03b_obj = new default03b_obj();
}

/* 
heap {
    record #0:1:default03b {
        field baz: default03b_obj = #1:default03b_obj;
    }
    record #1:2:default03b_obj {
        field foo: int[] = #null;
        field bar: bool = bool:true;
    }
} */
