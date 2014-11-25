// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

component default03a {
    field foo: int[];
    field bar: bool = foo == null;
}

/* 
heap {
    record #0:2:default03a {
        field foo: int[] = #null;
        field bar: bool = bool:true;
    }
} */
