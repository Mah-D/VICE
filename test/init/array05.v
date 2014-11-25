// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

component array05 {
    field a_02: bool[][] =  { {true, false}, {true} };
}

/* 
heap {
    record #0:1:array05 {
        field a_02: bool[][] = #1:bool[][2];
    }
    record #1:2:bool[][2] {
        field 0:bool[] = #2:bool[2];
        field 1:bool[] = #3:bool[1];
    }
    record #2:2:bool[2] {
        field 0:bool = bool:true;
        field 1:bool = bool:false;
    }
    record #3:1:bool[1] {
        field 0:bool = bool:true;
    }
} */
