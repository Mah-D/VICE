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

class Thief<E> extends Visitor<E> {
    method visitA(n: A, e: E) {
    }
    method visitB(n: B, e: E) {
    }
}
