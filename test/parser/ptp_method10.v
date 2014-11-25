// @Harness: v2-parse
// @Result: PASS

component method10 {
    method m1<X>(a: type<X, Y>, b: type<Y>): type<X> {
        return b;
    }
}
