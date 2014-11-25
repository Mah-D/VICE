// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

component default06a {
    field foo: char;
    field bar: char = foo;
}

/* 
heap {
    record #0:2:default06a {
        field foo: char = char:0;
        field bar: char = char:0;
    }
} */
