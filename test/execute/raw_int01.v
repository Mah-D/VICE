// @Harness: v2-exec
// @Test: promotion integer to 32 bit raw type
// @Result: 0=0, 1=1, 2=1, 3=1, 4=1, 5=1, 6=1, 7=1, 8=1, 9=1, 10=1, 11=1, 12=1, 13=0

component raw_int01 {
    field a: 32 = 0       ;
    field b: 32 = 1       ;
    field c: 32 = 7       ;
    field d: 32 = 15      ;
    field e: 32 = 16      ;
    field f: 32 = 255     ;
    field g: 32 = 32767   ;
    field h: 32 = 65536   ;
    field i: 32 = 1048576 ;
    field j: 32 = -1      ;
    field k: 32 = -65535  ;
    field l: 32 = -11     ;

    method main(arg: int): bool {
	local x: 32;
	if ( arg == 1 ) return  a == (x = 0);
	if ( arg == 2 ) return  b == (x = 1);
	if ( arg == 3 ) return  c == (x = 7);
	if ( arg == 4 ) return  d == (x = 15);
	if ( arg == 5 ) return  e == (x = 16);
	if ( arg == 6 ) return  f == (x = 255);
	if ( arg == 7 ) return  g == (x = 32767);
	if ( arg == 8 ) return  h == (x = 65536);
	if ( arg == 9 ) return  i == (x = 1048576);
	if ( arg == 10 ) return j == (x =  -1);
	if ( arg == 11 ) return k == (x =  -65535);
	if ( arg == 12 ) return l == (x =  -11);
	return false;
    }
}
