// @Harness: v2-parse
// @Result: PASS

class for4 {
  method m() {
     local a: int, b: int, c: int;

     for( a = 0; func(a); a = func(a) ) {
       local x = 0 + 0;
       for( b = 0; func(b); b = func(b) ) {
         local y = x + 0;
       }
       for( b = 0; func(b); b = func(b) ) {
         local y = x + 0;
       }
     }
  }
}
