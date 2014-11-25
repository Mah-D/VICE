// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 0=42, 1=11, 2=21, 3=31, 4=42

class A {
    method val(): int { return 11; }
}

class B extends A {
    method val(): int { return 21; }
}

class C extends A {
    method val(): int { return 31; }
}

component ptex_virt01 {
    field a: A = new A();
    field b: A = new B();
    field c: A = new C();

    method main(arg: int): int {
	if ( arg == 1 ) return a.val();
	if ( arg == 2 ) return b.val();
	if ( arg == 3 ) return c.val();
	return 42;
    }
}
