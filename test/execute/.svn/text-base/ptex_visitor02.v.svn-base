// @Harness: v2-exec
// @Result: 0=42, 1=11, 2=21, 3=12, 4=22, 5=42

class Visitor<E, R> {
    method visitA(n: A, e: E): R;
    method visitB(n: B, e: E): R;
}

class Item {
    method accept<E, R>(v: Visitor<E, R>, e: E): R;
}

class A extends Item {
    method accept<E, R>(v: Visitor<E, R>, e: E): R { return v.visitA(this, e); }
}

class B extends Item {
    method accept<E, R>(v: Visitor<E, R>, e: E): R { return v.visitB(this, e); }
}

class Env {
    field base: int;
    constructor(b: int) {
         base = b;
    }
}

class Printer extends Visitor<Env, int> {
    method visitA(n: A, e: Env): int {
        return 1 + e.base;
    }
    method visitB(n: B, e: Env): int {
        return 2 + e.base;
    }
}

component ptex_visitor02 {
    field a: Item = new A();
    field b: Item = new B();
    field v: Printer = new Printer();
    field e1: Env = new Env(10);
    field e2: Env = new Env(20);

    method main(arg: int): int {
	if ( arg == 1 ) return a.accept(v, e1);
	if ( arg == 2 ) return a.accept(v, e2);
	if ( arg == 3 ) return b.accept(v, e1);
	if ( arg == 4 ) return b.accept(v, e2);
	return 42;
    }
}
