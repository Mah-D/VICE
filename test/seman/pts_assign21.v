// @Harness: v2-seman
// @Result: TypeMismatch @ 8:14

class assign21<X, Y> {
   field f: X[];

   method m(x: Y) {
      f[0] = x;
      x = f[0];
   }
}
