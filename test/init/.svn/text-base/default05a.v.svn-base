// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

component default05a {
    field foo: char[] = new char[1];
    field bar: char = foo[0];
}

/* 
heap {
    record #0:2:default05a {
        field foo: char[] = #1:char[1];
        field bar: char = char:0;
    }
    record #1:1:int[1] {
        field 0: char = char:0;
    }
} */
