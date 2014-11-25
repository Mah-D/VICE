// @Harness: v2-seman
// @Result: PASS

class Self<T> {
    field me: T;
    constructor(t: T) {
        me = t;
    }
}

class A extends Self<A> {
    constructor() : super(this) { }
    method foo(): A { return me; }
}

class B extends Self<B> {
    constructor() : super(this) { }
    method foo(): B { return me; }
}
