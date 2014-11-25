// @Harness: v2-exec
// @Test: integer comparison operators
// @Result: 0=0, 1=1, 2=0, 3=1, 4=1, 5=1, 6=1, 7=0, 8=1, 9=1, 10=0

component comp03 {

    method op(a: int, b: int): bool {
	return a <= b;
    }

    method main(arg: int): bool {
	if ( arg == 1 ) return op(1, 2);
	if ( arg == 2 ) return op(2, 1);
	if ( arg == 3 ) return op(-1, 1);
	if ( arg == 4 ) return op(-1, 0);
	if ( arg == 5 ) return op(-200, -200);
	if ( arg == 6 ) return op(65535, 65535);
	if ( arg == 7 ) return op(14, 13);
	if ( arg == 8 ) return op(13, 14);
	if ( arg == 9 ) return op(-1255, -255);
	if ( arg == 10 ) return op(1000000, 48576);
	return false;
    }
}
