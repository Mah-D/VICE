// @Harness: v2-seman
// @Result: PASS

class Self<T> {
    method foo(): T;
}

class Myself extends Self<Myself> {
    method foo(): Myself { return this; }
}
