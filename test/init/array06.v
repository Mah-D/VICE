// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

component array06 {
    field a_02: char[][] =  { "hi", {'c'} };
}

/* 
heap {
    record #0:1:array06 {
        field a_02: char[][] = #1:char[][2];
    }
    record #1:2:char[][2] {
        field 0:char[] = #2:char[2];
        field 1:char[] = #3:char[1];
    }
    record #2:2:char[2] {
        field 0:char = char:104;
        field 1:char = char:105;
    }
    record #3:1:char[1] {
        field 0:char = char:99;
    }
} */
