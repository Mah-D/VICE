// @Harness: v2-parse
// @Result: PASS

class dowhile3 {
  method m(a: int, foo: bool) {

     do { do { } while ( true ); }  while ( true ) ;

     do { do { } while ( foo ); } while ( foo ) ;

     do { do { } while ( func(a) ); } while ( func(a) ) ;

     do {
       do {
         do {
         } while ( func(a) );
       } while ( func(a) );
     } while ( func(a) );
  }
}
