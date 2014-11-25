// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

component array09 {
    field a_01: char[] =  new char[1];
    field a_02: char[] =  new char[2];
    field a_03: char[] =  new char[3];
}

/* 
heap {
    record #0:3:array09 {
        field a_01: char[] = #1:char[1];
        field a_02: char[] = #2:char[2];
        field a_03: char[] = #3:char[3];
    }
    record #1:1:char[1] {
        field 0:char = char:0;
    }
    record #2:2:char[2] {
        field 0:char = char:0;
        field 1:char = char:0;
    }
    record #3:3:char[3] {
        field 0:char = char:0;
        field 1:char = char:0;
        field 2:char = char:0;
    }
} */
