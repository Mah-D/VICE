// @Harness: v2-seman
// @Result: PASS

class Visitor<E> {
    method visitA(n: A, e: E);
    method visitB(n: B, e: E);
}

class A {
    method accept<E>(v: Visitor<E>, e: E);
}

class B {
    method accept<E>(v: Visitor<E>, e: E);
}
