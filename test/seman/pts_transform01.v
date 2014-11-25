// @Harness: v2-seman
// @Result: PASS

class Transform<R, E> {
    method visitA(n: A, e: E): R;
    method visitB(n: B, e: E): R;
}

class A {
}

class B {
}
