// @Harness: v2-parse
// @Result: PASS

class switch1 {
  method m() {
     local a: int = -1, b: int, c: int;

     switch ( a ) {
     }

     switch ( a ) {
       default ;
     }

     switch ( a ) {
       case(0) ;
     }

     switch ( a ) {
       case(0) ;
       case(1) ;
     }

     switch ( a ) {
       case(0, 1) ;
     }

     switch ( a ) {
       case(0) ;
       case(1) ;
       default ;
     }

     switch ( a ) {
       case(0+0) ;
     }
  }
}
