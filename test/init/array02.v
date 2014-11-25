// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

component array02 {
    field a_01: bool[] = {true};
    field a_02: bool[] = {true, true, false};
}

/* 
heap {
    record #0:2:array02 {
        field a_01: bool[] = #1:bool[1];
        field a_02: bool[] = #2:bool[3];
    }
    record #1:1:bool[1] {
        field 0:bool = bool:true;
    }
    record #2:3:bool[3] {
        field 0:bool = bool:true;
        field 1:bool = bool:true;
        field 2:bool = bool:false;
    }
} */
