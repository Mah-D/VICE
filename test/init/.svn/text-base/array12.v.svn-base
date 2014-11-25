// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

component array12 {
    field a_01: bool[] = array();
    field a_02: bool[] = array();

    method array(): bool[] {
       local arr = {true, false};
       return arr;
    }
}

/* 
heap {
    record #0:2:array12 {
        field a_01: bool[] = #1:bool[2];
        field a_02: bool[] = #2:bool[2];
    }
    record #1:2:bool[2] {
        field 0:bool = bool:true;
        field 1:bool = bool:false;
    }
    record #2:2:bool[2] {
        field 0:bool = bool:true;
        field 1:bool = bool:false;
    }
} */
