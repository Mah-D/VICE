// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=13, 2=15, 3=42

class field10_obj {
    method bar(i: int) { field10.f = i; }
}

component field10 {
    field f: int = 42;
    field foo: function(int) = new field10_obj().bar;

    method main(arg: int): int {
	if ( arg == 1 ) foo(13);
	if ( arg == 2 ) foo(15);
	return f;
    }
}
