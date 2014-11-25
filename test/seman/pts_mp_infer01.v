// @Harness: v2-seman
// @Result: PASS

component mp_infer01 {
    method first<X, Y>(a: X, b: Y): X {
        return a;
    }
}
