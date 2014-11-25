// @Harness: v2-seman
// @Result: TypeMismatch @ 7:9

class assign18<X> {
   field f: assign18<X>;
   method m(x: assign18<int>) {
      f = x;
   }
}
