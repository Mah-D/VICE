// @Harness: v2-seman
// @Result: TypeMismatch @ 7:9

class assign16<X> {
   field f: X;
   method m(x: int) {
      f = x;
   }
}
