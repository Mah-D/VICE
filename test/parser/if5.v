// @Harness: v2-parse
// @Result: PASS

class if5 {
  method m() {
    local a: int;
    
    if ( true ) { if ( false ) ; }
    else if ( true ) { if ( false ) ; else ; }
    else if ( false ) a = 0;
    else a = 1;
  }
}
