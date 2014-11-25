// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=13, 1=13, 2=13

class field01_obj {
}

component field01 {
    field foo: field01_obj = new field01_obj();

    method main(arg: int): int {
	return foo != null ? 13 : 77;
    }
}
