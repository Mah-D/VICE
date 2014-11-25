// @Harness: v2-parse
// @Result: PASS

class dowhile2 {
  method m(a: int, foo: bool) {

     do {
       local x = a;
     } while ( true ) ;

     do {
       local x = a;
       x = x + 1;
     } while ( foo ) ;
  }
}
