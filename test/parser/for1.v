// @Harness: v2-parse
// @Result: PASS

class for1 {
  method m() {
     for( ; ; ) ;
  }
  method n() {
     for( ; ; ) { }
  }
  method o() {
     for( a = 0; ; ) ;
  }
  method p() {
     for( a = 0; a < 2; ) ;
     for( a = 0; func(a); ) ;
     for( a = 0; a < 2; a++) ;
     for( a = 0; func(a); a = func(a)) ;

  }
}
