// @Harness: v2-parse
// @Result: PASS

class for2 {
  method m() {
     local a: int;

     for( a = 0; func(a); a = func(a))
       a = func(a,a);

     for( a = 0; func(a); a = func(a)) {
       a = func(a,a);
     }

     for( a = 0; func(a); a = func(a)) {
       local c = func(a,a);
     }

     for( a = 0; func(a); a = func(a)) {
       local c = func(a,a);
       c = func(c,c) + 0;
     }

  }
}
