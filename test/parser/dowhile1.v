// @Harness: v2-parse
// @Result: PASS

class dowhile1 {
  method m(a: int, foo: bool) {

     do {} while ( true ) ;
     do {} while ( foo ) ;
     do {} while ( func(a) ) ;
  }
}
