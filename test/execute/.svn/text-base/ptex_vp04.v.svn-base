// @Harness: v2-exec
// @Result: 0=42, 1=11, 2=12, 3=13, 4=42

class NX {
   method id<X, Y>(x: X, y: Y): X;
}

class MX extends NX {
   method id<X, Y>(x: X, y: Y): X {
      return x;
   }
}

component ptex_vp04 {
   field m1: NX = new MX();
   field m2: int = m1.id(11, 8);
   field m3: int = m1.id(13, 9);
   
   method main(arg: int): int {
      if ( arg == 1 ) return m2;
      if ( arg == 2 ) return m1.id(12, 8);
      if ( arg == 3 ) return m1.id(m3, 9);
      return 42;
   }
}
