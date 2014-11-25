// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=13, 2=14, 3=15, 4=16, 5=17, 6=18, 7=19, 8=20, 9=21, 10=22, 11=23, 12=24, 13=25, 14=4, 15=3, 16=6, 17=42 

component array17 {
    field a: int[][] =  new int[][4];
    field b: bool[][] =  new bool[][3];
    field c: char[][] = new char[][6];

    method main(arg: int): int {
	if ( arg == 1 ) return a[0] == null ? 13 : 77;
	if ( arg == 2 ) return a[1] == null ? 14 : 77;
	if ( arg == 3 ) return a[2] == null ? 15 : 77;
	if ( arg == 4 ) return a[3] == null ? 16 : 77;

	if ( arg == 5 ) return b[0] == null ? 17 : 77;
	if ( arg == 6 ) return b[1] == null ? 18 : 77;
	if ( arg == 7 ) return b[2] == null ? 19 : 77;

	if ( arg == 8 )  return c[0] == null ? 20 : 77;
	if ( arg == 9 )  return c[1] == null ? 21 : 77;
	if ( arg == 10 ) return c[2] == null ? 22 : 77;
	if ( arg == 11 ) return c[3] == null ? 23 : 77;
	if ( arg == 12 ) return c[4] == null ? 24 : 77;
	if ( arg == 13 ) return c[5] == null ? 25 : 77;

	if ( arg == 14 ) return a.length;
	if ( arg == 15 ) return b.length;
	if ( arg == 16 ) return c.length;

	return 42;
    }
}
