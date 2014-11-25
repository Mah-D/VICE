// @Harness: v2-exec
// @Result: 0=42, 1=11, 2=21, 3=12, 4=22, 5=42

class Visitor<E> {
    method visitA(n: A, e: E);
    method visitB(n: B, e: E);
}

class Item {
    method accept<E>(v: Visitor<E>, e: E);
}

class A extends Item {
    method accept<E>(v: Visitor<E>, e: E) { v.visitA(this, e); }
}

class B extends Item {
    method accept<E>(v: Visitor<E>, e: E) { v.visitB(this, e); }
}

class Env {
    field base: int;
    constructor(b: int) {
         base = b;
    }
}

class Printer extends Visitor<Env> {
    method visitA(n: A, e: Env) {
        ptex_visitor01.result = 1 + e.base;
    }
    method visitB(n: B, e: Env) {
        ptex_visitor01.result = 2 + e.base;
    }
}

component ptex_visitor01 {
    field result: int = 42;
    field a: Item = new A();
    field b: Item = new B();
    field v: Printer = new Printer();
    field e1: Env = new Env(10);
    field e2: Env = new Env(20);

    method main(arg: int): int {
	if ( arg == 1 ) a.accept(v, e1);
	if ( arg == 2 ) a.accept(v, e2);
	if ( arg == 3 ) b.accept(v, e1);
	if ( arg == 4 ) b.accept(v, e2);
	return result;
    }
}
