// @Harness: v2-parse
// @Result: PASS

class while3 {
  method m() {
     local a: int = 7;
     local foo: bool = false;

     while ( true ) while ( true ) ;
     while ( true ) while ( true ) { }

     while ( foo ) while ( foo ) ;
     while ( foo ) while ( foo ) { }

     while ( func(a) ) while ( func(a) ) ;
     while ( func(a) ) while ( func(a) ) { }

     while ( func(a) ) {
       while ( func(a) ) {
         while ( func(a) ) {
         }
       }
     }
  }
}
