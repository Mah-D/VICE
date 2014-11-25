// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

component field05 {
    field foo: char[] = "hello";
}

/* 
heap {
    record #0:1:field05 {
        field foo: char[] = #1:char[5];
    }
    record #1:5:char[5] {
        field 0: char = char:104;
        field 1: char = char:101;
        field 2: char = char:108;
        field 3: char = char:108;
        field 4: char = char:111;
    }
} */
