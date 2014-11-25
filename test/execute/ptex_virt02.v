// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 0=42, 1=UnimplementedException, 2=21, 3=31, 4=42

class A<T> {
    method val(): T;
}

class B extends A<int> {
    method val(): int { return 21; }
}

class C extends A<int> {
    method val(): int { return 31; }
}

component ptex_virt02 {
    field a: A<int> = new A<int>();
    field b: A<int> = new B();
    field c: A<int> = new C();

    method main(arg: int): int {
	if ( arg == 1 ) return a.val();
	if ( arg == 2 ) return b.val();
	if ( arg == 3 ) return c.val();
	return 42;
    }
}
