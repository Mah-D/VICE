// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

component array08 {
    field a_01: bool[] = new bool[1];
    field a_02: bool[] = new bool[3];
}

/* 
heap {
    record #0:2:array08 {
        field a_01: bool[] = #1:bool[1];
        field a_02: bool[] = #2:bool[3];
    }
    record #1:1:bool[1] {
        field 0:bool = bool:false;
    }
    record #2:3:bool[3] {
        field 0:bool = bool:false;
        field 1:bool = bool:false;
        field 2:bool = bool:false;
    }
} */
