// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=13, 2=17, 3=42

component field08 {
    field foo: function(int): int = bar;

    method main(arg: int): int {
	if ( arg == 1 ) return foo(13);
	if ( arg == 2 ) return foo(17);
	return 42;
    }

    method bar(i: int): int { return i; }
}
