// @Harness: v2-seman
// @Result: TypeMismatch @ 7:9

class assign19<X, Y> {
   field f: assign19<X, Y>;
   method m(x: assign19<Y, X>) {
      f = x;
   }
}
