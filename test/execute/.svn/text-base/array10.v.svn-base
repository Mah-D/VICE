// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=0, 2=0, 3=0, 4=0, 5=42

component array10 {
    field a: int[] =  {};
    field b: bool[] =  {};
    field c: char[] = {};
    field d: char[] = "";

    method main(arg: int): int {
	if ( arg == 1 ) return a.length;
	if ( arg == 2 ) return b.length;
	if ( arg == 3 ) return c.length;
	if ( arg == 4 ) return d.length;
	return 42;
    }
}
