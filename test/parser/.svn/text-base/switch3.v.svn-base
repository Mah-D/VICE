// @Harness: v2-parse
// @Result: PASS

class switch3 {
  method m() {
     local b: int, c: int = -77;

     switch ( 0 ) {
       default { local d = 1; c = c+1;}
     }

     switch ( 0 ) {
       case(0) { local d = 1; c = c+1;}
     }

     switch ( 0 ) {
       case(0) { local d = 1; c = c+1;}
       case(1) { b = 1; }
       case(2) { c = 1; }
     }

     switch ( 0 ) {
       case(0) { local d = 1; c = c+1;}
       case(1, 2) { b = 1; }
     }

     switch ( 0 ) {
       case(0) { local d = 1; c = c+1;}
       case(1) { c = 1; }
       default { c = 0; }
     }

     switch ( 0 ) {
       case(0+0) { local d = 1; }
     }
  }
}
