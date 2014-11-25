// @Harness: v2-parse
// @Result: PASS

class if1 {
  method m() {
    local a: int = 0;
    
    if ( true ) ;
    if ( func(a) ) ;

    if ( true ) ; else ;
    if ( func(a) ) ; else ;

    if ( true ) a = 0; else a = 1;
    if ( func(a) ) a = 0; else a = 1;
  }
}
