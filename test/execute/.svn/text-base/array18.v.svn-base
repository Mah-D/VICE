// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=10, 2=11, 3=12, 4=13, 5=14, 6=15, 7=42

component array18 {
    field matrix: int[][] = new int[2][3];

    method compute() {
	local i: int, j: int, cnt = 0;
	for ( i = 0; i < 2; i++ ) {
	    for ( j = 0; j < 3; j++ ) 
            	matrix[i][j] = 10 + cnt++;
        }
    }

    method main(arg: int): int {
	compute();
	if ( arg == 1 ) return matrix[0][0];
	if ( arg == 2 ) return matrix[0][1];
	if ( arg == 3 ) return matrix[0][2];
	if ( arg == 4 ) return matrix[1][0];
	if ( arg == 5 ) return matrix[1][1];
	if ( arg == 6 ) return matrix[1][2];
	return 42;
    }
}
