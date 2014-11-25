// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

component array03 {
    field a_01: char[] =  {'1'};
    field a_02: char[] =  {'1', '2'};
    field a_03: char[] =  "hi";
}

/* 
heap {
    record #0:3:array03 {
        field a_01: char[] = #1:char[1];
        field a_02: char[] = #2:char[2];
        field a_03: char[] = #3:char[2];
    }
    record #1:1:char[1] {
        field 0:char = char:49;
    }
    record #2:2:char[2] {
        field 0:char = char:49;
        field 1:char = char:50;
    }
    record #3:2:char[2] {
        field 0:char = char:104;
        field 1:char = char:105;
    }
} */
