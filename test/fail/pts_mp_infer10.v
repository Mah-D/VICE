// @Harness: v2-seman
// @Result: PASS

component mp_infer10 {
    method apply<T>(a: T, f: function(T): T): T {
        return f(a);
    }
    method test() {
        local x: int = apply(0, id);
    }
    method id<T>(x: T): T { return x; }
}
