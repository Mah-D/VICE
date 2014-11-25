// @Harness: v2-exec
// @Test: pre/post increment operations
// @Result: 0=0, 1=7, 2=42, 3=6, 4=1, 5=0

component prepost17 {
    field cnt: int;
    field foo: int[] = { 6, 42 };
    field bar: int;
    
    method getfoo(): int[] {
	cnt++; // should only be executed once
	return foo;
    }

    method main(arg: int): int {
	bar = getfoo()[0]++;
	if ( arg == 1 ) return foo[0];
	if ( arg == 2 ) return foo[1];
	if ( arg == 3 ) return bar;
	if ( arg == 4 ) return cnt;
	return 0;
    }
}
