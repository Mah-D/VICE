// @Harness: v2-seman
// @Result: PASS

class Visitor<E> {
    method visitA(n: A, e: E);
    method visitB(n: B, e: E);
}

class A {
}

class B {
}
