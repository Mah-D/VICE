// @Harness: v2-seman
// @Result: PASS

class Self<T> {
    method foo(): T;
}

class A extends Self<A> {
    method foo(): A { return this; }
}

class B extends Self<B> {
    method foo(): B { return this; }
}
