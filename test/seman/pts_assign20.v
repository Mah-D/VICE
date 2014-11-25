// @Harness: v2-seman
// @Result: TypeMismatch @ 6:18

class assign20<X, Y> {
   field f: Y;
   field g: X = m(f);

   method m(x: X): X {
      return x;
   }
}
