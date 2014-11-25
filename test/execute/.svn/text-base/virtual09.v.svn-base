// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 0=42, 1=11, 2=12, 3=13, 4=14, 5=42

class A {
    method m(c: C): int { return 11; }
}

class B extends A {
    method m(c: C): int { return 12; }
}

class C {
    method n(a: A): int { return 13; }
}

class D extends C {
    method n(a: A): int { return 14; }
}

component virtual09 {
    field a: A = new A();
    field b: A = new B();
    field c: C = new C();
    field d: C = new D();

    method main(arg: int): int {
	if ( arg == 1 ) return a.m(c);
	if ( arg == 2 ) return b.m(d);
	if ( arg == 3 ) return c.n(a);
	if ( arg == 4 ) return d.n(b);
	return 42;
    }
}
