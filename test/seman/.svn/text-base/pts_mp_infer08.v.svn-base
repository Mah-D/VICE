// @Harness: v2-seman
// @Result: PASS

component mp_infer08 {
    method apply<T>(a: T, f: function(T): T): T {
        return f(a);
    }
    method test() {
        local x: int = apply(0, null);
    }
}
