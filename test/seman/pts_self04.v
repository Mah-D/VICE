// @Harness: v2-seman
// @Result: PASS

class Self<T> {
}

class Myself<T> extends Self<Myself<int> > {
}
