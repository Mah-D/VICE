// @Harness: v2-parse
// @Result: PASS

class while4 {
  method m() {
     local a: int = 9;
     local foo: bool = false;

     while ( true ) {
       local x = a;

       while ( true ) {
         local y = a;
       }

       while ( foo ) {
         local y = a;
         x = x + 1;
       }

     }
  }
}
