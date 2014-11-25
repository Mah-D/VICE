// @Harness: v2-exec
// @Result: 0=42, 1=11, 2=12, 3=13, 4=42

class NX {
   method id<X>(x: X): X;
}

class MX extends NX {
   method id<X>(x: X): X {
      return x;
   }
}

component ptex_vp03 {
   field m1: NX = new MX();
   field m2: int = m1.id(11);
   field m3: int = m1.id(13);
   
   method main(arg: int): int {
      if ( arg == 1 ) return m2;
      if ( arg == 2 ) return m1.id(12);
      if ( arg == 3 ) return m1.id(m3);
      return 42;
   }
}
