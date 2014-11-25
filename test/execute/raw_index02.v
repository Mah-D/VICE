// @Harness: v2-exec
// @Test: initialization interpreter > raw types > and operator
// @Result: 0=0, 1=0, 2=0, 3=0, 4=1, 5=1, 6=1, 7=1

component raw_index02 {
    field foo: 8 = 0xf0;

    method main(arg: int): 1 {
	return foo[arg];
    }
}
