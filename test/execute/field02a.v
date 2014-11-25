// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=13, 2=17, 3=42

component field02a {
    field a: int;
    field b: int;

    constructor() {
        a = 13;
	b = 17;
    }

    method main(arg: int): int {
	if ( arg == 1 ) return a;
	if ( arg == 2 ) return b;
	return 42;
    }
}
