// @Harness: v2-seman
// @Result: PASS

component mp_infer12 {
    method apply<T>(a: T, f: function(T): T): T {
        return f(a);
    }
    method test() {
        local x = apply(0, id);
    }
    method id<U>(x: U): U { return x; }
}
