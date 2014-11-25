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
    method accept<R, E>(v: Transform<R, E>, e: E): R;
}

class B extends S {
    method accept<R, E>(v: Transform<R, E>, e: E): R;
}
