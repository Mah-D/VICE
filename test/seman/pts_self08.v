// @Harness: v2-seman
// @Result: PASS

class Self<T> {
    field me: T;
}

class A extends Self<A> {
    constructor() { me = this; }
    method foo(): A { return me; }
}

class B extends Self<B> {
    constructor() { me = this; }
    method foo(): B { return me; }
}
