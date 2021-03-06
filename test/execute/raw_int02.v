// @Harness: v2-exec
// @Test: promotion integer to 32 bit raw type
// @Result: 0=0, 1=1, 2=1, 3=1, 4=1, 5=1, 6=1, 7=1, 8=1, 9=1, 10=1, 11=1, 12=1, 13=0

component raw_int02 {
    field a: 32 = 0       | 1;
    field b: 32 = 1       | 2;
    field c: 32 = 7       | 3;
    field d: 32 = 15      | 4;
    field e: 32 = 16      | 5;
    field f: 32 = 255     | 6;
    field g: 32 = 32767   | 7;
    field h: 32 = 65536   | 8;
    field i: 32 = 1048576 | 9;
    field j: 32 = -1      | 10;
    field k: 32 = -65535  | 11;
    field l: 32 = -11     | 12;

    method main(arg: int): bool {
	if ( arg == 1 ) return  a == (0       | arg); 
	if ( arg == 2 ) return  b == (1       | arg); 
	if ( arg == 3 ) return  c == (7       | arg); 
	if ( arg == 4 ) return  d == (15      | arg); 
	if ( arg == 5 ) return  e == (16      | arg); 
	if ( arg == 6 ) return  f == (255     | arg); 
	if ( arg == 7 ) return  g == (32767   | arg); 
	if ( arg == 8 ) return  h == (65536   | arg); 
	if ( arg == 9 ) return  i == (1048576 | arg); 
	if ( arg == 10 ) return j == (-1      | arg); 
	if ( arg == 11 ) return k == (-65535  | arg); 
	if ( arg == 12 ) return l == (-11     | arg); 
	return false;
    }
}
