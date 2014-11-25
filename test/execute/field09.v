// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=13, 2=17, 3=42

class field09_obj {
    method bar(i: int): int { return i; }
}

component field09 {
    field foo: function(int): int = new field09_obj().bar;
    method main(arg: int): int {
	if ( arg == 1 ) return foo(13);
	if ( arg == 2 ) return foo(17);
	return 42;
    }
}
