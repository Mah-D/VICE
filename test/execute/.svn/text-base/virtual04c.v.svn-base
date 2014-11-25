// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 13

class virtual04c_1 {
    field th: virtual04c_1;
    method val(a: int): int { th = this; return a+1; }
}

class virtual04c_2 extends virtual04c_1 {
    method val(a: int): int { th = this; return a+2; }
}

class virtual04c_3 extends virtual04c_1 {
    method val(a: int): int { th = this; return a+3; }
}

component virtual04c {
    field a: virtual04c_1 = new virtual04c_1();
    field b: virtual04c_1 = new virtual04c_2();
    field c: virtual04c_1 = new virtual04c_3();

    method main(arg: int): int {
	return c.val(10);
    }
}
