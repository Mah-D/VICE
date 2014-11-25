// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

component array18 {
    field matrix: int[][] =  new int[2][3];
    constructor() {
	local i: int, j: int, cnt = 0;
	for ( i = 0; i < 2; i++ ) {
	    for ( j = 0; j < 3; j++ ) 
            	matrix[i][j] = cnt++;
        }
    }
}

/* 
heap {
    record #0:1:array18 {
        field matrix: int[][] = #1:int[][2];
    }
    record #1:2:int[][2] {
        field 0:int[] = #2:int[3];
        field 1:int[] = #3:int[3];
    }
    record #2:3:int[3] {
        field 0:int = int:0;
        field 1:int = int:1;
        field 2:int = int:2;
    }
    record #3:3:int[3] {
        field 0:int = int:3;
        field 1:int = int:4;
        field 2:int = int:5;
    }
} */
