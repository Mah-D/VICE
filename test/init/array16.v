// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

component array16 {
    field a_02: char[][] = new char[2][6];
}

/* 
heap {
    record #0:1:array16 {
        field a_02: char[][] = #1:char[][2];
    }
    record #1:2:char[][2] {
        field 0:char[] = #2:char[6];
        field 1:char[] = #3:char[6];
    }
    record #2:6:char[6] {
        field 0:char = char:0;
        field 1:char = char:0;
        field 2:char = char:0;
        field 3:char = char:0;
        field 4:char = char:0;
        field 5:char = char:0;
    }
    record #3:6:char[6] {
        field 0:char = char:0;
        field 1:char = char:0;
        field 2:char = char:0;
        field 3:char = char:0;
        field 4:char = char:0;
        field 5:char = char:0;
    }
} */
