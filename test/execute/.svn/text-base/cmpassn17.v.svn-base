// @Harness: v2-exec
// @Test: pre/post increment operations
// @Result: 0=0, 1=8, 2=42, 3=1, 4=1, 5=0

component cmpassn17 {
    field foocnt: int;
    field indcnt: int;

    field foo: int[] = { 6, 42 };
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
	getfoo()[getind()] += 2;
	if ( arg == 1 ) return foo[0];
	if ( arg == 2 ) return foo[1];
	if ( arg == 3 ) return foocnt;
	if ( arg == 4 ) return indcnt;
	return 0;
    }
}
