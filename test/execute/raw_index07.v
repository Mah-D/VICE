// @Harness: v2-exec
// @Test: initialization interpreter > raw types > and operator
// @Result: 0=3, 1=3, 2=7, 3=11, 4=19, 5=35, 6=67, 7=131

class raw_index07_a {
    field foo: 8 = 0x03;
}

component raw_index07 {
    field f: raw_index07_a = new raw_index07_a();

    method main(arg: int): 8 {
	f.foo[arg] |= 0b1;
	return f.foo;
    }
}
