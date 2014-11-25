// @Harness: v2-parse
// @Result: PASS

class if4 {
  method m() {
    local a: int = 0;
    
    if ( true ) { if ( false ) ; }

    if ( true ) { if ( false ) ; else ; }

    if ( true ) { if ( false ) ; } else ;

    if ( true ) { if ( false ) ; else ; } else ;

    if ( func(a) ) { if ( func(a) ) ; }

    if ( func(a) ) { if ( func(a) ) ; else ; }

    if ( func(a) ) { if ( func(a) ) ; } else ; 

    if ( func(a) ) { if ( func(a) ) ; else ; } else ;
  }
}
