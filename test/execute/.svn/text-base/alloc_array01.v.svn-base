// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=6, 2=7, 3=11, 4=13, 5=2, 6=2, 7=42

component alloc_array01 {
    field a: int[] = array(6, 7);
    field b: int[] = array(11, 13);

    method array(c: int, d: int): int[] {
	local arr = {c, d};
        return arr;
    }

    method main(arg: int): int {
	if ( arg == 1 ) return a[0];
	if ( arg == 2 ) return a[1];
	if ( arg == 3 ) return b[0];
	if ( arg == 4 ) return b[1];
	if ( arg == 5 ) return a.length;
	if ( arg == 6 ) return b.length;
	return 42;
    }
}
