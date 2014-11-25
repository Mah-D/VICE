// @Harness: v2-seman
// @Result: PASS

class assign09<X> {
   field f: X;
   field g: X = m(g);

   method m(x: X): X {
      return x;
   }
}
