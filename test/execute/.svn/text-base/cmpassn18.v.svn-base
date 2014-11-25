// @Harness: v2-exec
// @Test: pre/post increment operations
// @Result: 0=0, 1=6, 2=42, 3=6, 4=1, 5=1, 6=0

component cmpassn18 {
    field foocnt: int;
    field indcnt: int;

    field foo: int[] = { 8, 42 };
    field bar: int;
    
    method getfoo(): int[] {
	foocnt++;
	return foo;
    }
    
    method getind(): int {
	indcnt++;
	return 0;
    }

    method main(arg: int): int {
	bar = getfoo()[getind()] -= 2;
	if ( arg == 1 ) return foo[0];
	if ( arg == 2 ) return foo[1];
	if ( arg == 3 ) return bar;
	if ( arg == 4 ) return foocnt;
	if ( arg == 5 ) return indcnt;
	return 0;
    }
}
