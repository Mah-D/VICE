// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=0, 1=1, 2=0, 3=0, 4=1, 5=0

component alloc_array02 {
    field a: bool[] = array(true, false);
    field b: bool[] = array(false, true);

    method array(c: bool, d: bool): bool[] {
       local arr = {c, d};
       return arr;
    }

    method main(arg: int): bool {
	if ( arg == 1 ) return a[0];
	if ( arg == 2 ) return a[1];
	if ( arg == 3 ) return b[0];
	if ( arg == 4 ) return b[1];
	return false;
    }
}
