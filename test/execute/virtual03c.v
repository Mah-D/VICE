// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 13

class virtual03c_1 {
    method val(a: int): int { return a+1; }
}

class virtual03c_2 extends virtual03c_1 {
    method val(a: int): int { return a+2; }
}

class virtual03c_3 extends virtual03c_1 {
    method val(a: int): int { return a+3; }
}

component virtual03c {
    field a: virtual03c_1 = new virtual03c_1();
    field b: virtual03c_1 = new virtual03c_2();
    field c: virtual03c_1 = new virtual03c_3();

    method main(arg: int): int {
	return c.val(10);
    }
}
