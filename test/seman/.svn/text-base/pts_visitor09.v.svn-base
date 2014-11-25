// @Harness: v2-seman
// @Result: PASS

class Visitor<E> {
    method visitA(n: A, e: E);
    method visitB(n: B, e: E);
}

class S {
    method accept<E>(v: Visitor<E>, e: E);
}

class A extends S {
    method accept<E>(v: Visitor<E>, e: E) { v.visitA(this, e); }
}

class B extends S {
    method accept<E>(v: Visitor<E>, e: E) { v.visitB(this, e); }
}

class PriceList {
}

class Thief extends Visitor<PriceList> {
    method visitA(n: A, e: PriceList) {
    }
    method visitB(n: B, e: PriceList) {
    }
}

component Client {
    field a: S = new A();
    field b: S = new B();
    field thief: Thief = new Thief();

    method test() {
	a.accept(thief, new PriceList());
	b.accept(thief, new PriceList());
    }
}
