// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

component default02a {
    field foo: bool;
    field bar: bool = !foo;
}

/* 
heap {
    record #0:2:default02a {
        field foo: bool = bool:false;
        field bar: bool = bool:true;
    }
} */
