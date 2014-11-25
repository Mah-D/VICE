// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=0, 1=13, 2=14, 3=15, 4=16, 5=17, 6=18, 7=0

component array22 {
    field a: int[] =  new int[4];
    field b: int[] =  new int[6];

    method fill(a: int[], v: int) {
	local i = 0;
        for ( ; i < a.length; i++ ) a[i] = v;
    }

    method main(arg: int): int {
	if ( arg == 1 ) fill(a, 13);
	if ( arg == 2 ) fill(b, 14);
	if ( arg == 3 ) fill(a, 15);
	if ( arg == 4 ) fill(b, 16);
	if ( arg == 5 ) fill(a, 17);
	if ( arg == 6 ) fill(b, 18);
	return a[a.length - 1] + b[b.length - 1];
    }
}
