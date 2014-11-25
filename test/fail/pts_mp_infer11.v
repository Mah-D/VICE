// @Harness: v2-seman
// @Result: PASS

component mp_infer11 {
    method apply<T>(a: T, f: function(T): T): T {
        return f(a);
    }
    method test() {
        local x = apply(0, id);
    }
    method id<T>(x: T): T { return x; }
}
