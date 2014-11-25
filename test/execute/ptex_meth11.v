// @Harness: v2-exec
// @Result: 0=42, 1=1, 2=2, 3=4, 4=5, 5=21, 6=21, 7=21, 8=21, 9=21, 10=21, 11=42

component ptex_meth11 {

    method main(arg: int): int {
    	if ( arg == 1 )  return m(  0b1) :: int;
    	if ( arg == 2 )  return m( 0b10) :: int;
    	if ( arg == 3 )  return m(0b100) :: int;
    	if ( arg == 4 )  return m(       0x5) :: int;
    	if ( arg == 5 )  return m(      0x15) :: int;
    	if ( arg == 6 )  return m(     0x015) :: int;
    	if ( arg == 7 )  return m(    0x0015) :: int;
    	if ( arg == 8 )  return m(   0x00015) :: int;
    	if ( arg == 9 )  return m(  0x000015) :: int;
    	if ( arg == 10 ) return m(0x00000015) :: int;
        return 42;
    }

    method m<T>(x: T): T { return x; }
}
