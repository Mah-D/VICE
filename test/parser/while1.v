// @Harness: v2-parse
// @Result: PASS

class while1 {
  method m() {
     local a: int = -1;
     local foo: bool = true;

     while ( true ) ;
     while ( foo ) ;
     while ( func(a) ) ;

     while ( true ) { }
     while ( foo ) { }
     while ( func(a) ) { }

  }
}
