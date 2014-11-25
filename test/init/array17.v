// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

component array17 {
    field a_01: int[][] =  new int[][4];
    field a_02: bool[][] =  new bool[][3];
    field a_03: char[][] = new char[][6];
}

/* 
heap {
    record #0:3:array17 {
        field a_01: int[][] = #1:int[][4];
        field a_02: bool[][] = #2:bool[][3];
        field a_03: char[][] = #3:char[][6];
    }
    record #1:4:int[][4] {
	field 0:int[] = #null;
	field 1:int[] = #null;
	field 2:int[] = #null;
	field 3:int[] = #null;
    }
    record #2:3:bool[][3] {
	field 0:bool[] = #null;
	field 1:bool[] = #null;
	field 2:bool[] = #null;
    }
    record #3:6:char[][6] {
	field 0:char[] = #null;
	field 1:char[] = #null;
	field 2:char[] = #null;
	field 3:char[] = #null;
	field 4:char[] = #null;
	field 5:char[] = #null;
    }
} */
