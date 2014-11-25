// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 0=42, 1=42, 2=21, 3=31, 4=42

class abstract01_1 {
    method val(): int;
}

class abstract01_2 extends abstract01_1 {
    method val(): int { return 21; }
}

class abstract01_3 extends abstract01_1 {
    method val(): int { return 31; }
}

component abstract01 {
    field b: abstract01_1 = new abstract01_2();
    field c: abstract01_1 = new abstract01_3();

    method main(arg: int): int {
	if ( arg == 2 ) return b.val();
	if ( arg == 3 ) return c.val();
	return 42;
    }
}
