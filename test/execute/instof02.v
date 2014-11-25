// @Harness: v2-exec
// @Test: operation of instanceof construct
// @Result: 1

class instof02_obj {
}

component instof02 {
    field foo: instof02_obj = new instof02_obj();
    method main(arg: int): int {
	return foo instanceof instof02_obj ? 1 : 0;
    }
}
