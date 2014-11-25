// @Harness: v2-seman
// @Result: PASS

class Transform<R, E> {
    method visitA(n: A, e: E): R;
    method visitB(n: B, e: E): R;
}

class S {
    method accept<R, E>(v: Transform<R, E>, e: E): R;
}

class A extends S {
    method accept<R, E>(v: Transform<R, E>, e: E): R { return v.visitA(this, e); }
}

class B extends S {
    method accept<R, E>(v: Transform<R, E>, e: E): R { return v.visitB(this, e); }
}

class PriceList {
}

class Precious extends PriceList {
}

class Jewels {
}

class Thief extends Transform<Jewels, PriceList> {
    method visitA(n: A, e: PriceList): Jewels {
	return null;
    }
    method visitB(n: B, e: PriceList): Jewels {
	return null;
    }
}

component Client {
    field a: S = new A();
    field b: S = new B();
    field thief: Thief = new Thief();

    method test() {
	local j1: Jewels = a.accept(thief, new Precious());
	local j2: Jewels = b.accept(thief, new Precious());
    }
}
