// @Harness: v2-parse
// @Result: PASS

class method08 {
    method m1<X, Y, Z>(a: type<X, Y>, b: type<Y>): type<X> {
        return b;
    }
}
