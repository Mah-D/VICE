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
    method accept<E>(v: Visitor<E>, e: E);
}

class B extends S {
    method accept<E>(v: Visitor<E>, e: E);
}