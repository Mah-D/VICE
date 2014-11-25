// @Harness: v2-seman
// @Result: PASS

component mp_infer02 {
    method first<X, Y>(a: X, b: Y): X {
        return a;
    }
    method second<X, Y>(a: X, b: Y): Y {
        return b;
    }
}
