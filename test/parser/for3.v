// @Harness: v2-parse
// @Result: PASS

class for3 {
  method m() {
     for ( ; ; ) for ( ; ; ) ;
  }
  method n() {
     for( a = 0; func(a); a = func(a) ) {
       for( b = 0; func(b); b = func(b) ) {
       }
     }

     for( a = 0; func(a); a = func(a)) {
       for( b = 0; func(b); b = func(b) ) {
         for( c = 0; func(c); c = func(c) ) {
         }
       }
     }
  }
}
