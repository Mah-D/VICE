// @Harness: v2-parse
// @Result: PASS

class switch2 {
  method m() {
     local a: int = 0, b: int, c: int;

     switch ( a ) {
       default b = 1;
     }

     switch ( a ) {
       case(0) b = 1;
     }

     switch ( a ) {
       case(0) b = 1;
       case(1) c = 1;
     }

     switch ( a ) {
       case(0, 1) b = 1;
     }

     switch ( a ) {
       case(0) b = 1;
       case(1) c = 1;
       default c = 0;
     }

     switch ( a ) {
       case(0+0) b = 1;
     }
  }
}
