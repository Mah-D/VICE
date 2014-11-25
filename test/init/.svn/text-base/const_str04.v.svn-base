// @Harness: v2-init
// @Test: compile-time constants for primitive types
// @Result: PASS

component const_str01 {
    field str_1: char[] = "";
    field str_2: char[] = "\n\t\b\r\"\377";
}

/* 
heap {
    record #0:2:const_str01 {
        field str_1: char[] = #1:char[0];
        field str_2: char[] = #2:char[6];
    }
    record #1:0:char[0] {
    }
    record #2:6:char[6] {
        field 0:char = char:10;
        field 1:char = char:9;
        field 2:char = char:8;
        field 3:char = char:13;
        field 4:char = char:34;
        field 5:char = char:255;
    }
} */
