// @Harness: v2-parse
// @Result: PASS

class method07 {
    method m1<X, Y>(a: type<X, Y>, b: type<Y>): type<X> {
        return b;
    }
}
