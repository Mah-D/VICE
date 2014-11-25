// @Harness: v2-exec
// @Test: integer comparison operators
// @Result: 0=0, 1=1, 2=0, 3=1, 4=1, 5=1, 6=1, 7=0

component comp11 {

    method op(a: char, b: char): bool {
	return a != b;
    }

    method main(arg: int): bool {
	if ( arg == 1 ) return op('1', '2');
	if ( arg == 2 ) return op('1', '1');
	if ( arg == 3 ) return op('-', '1');
	if ( arg == 4 ) return op('\n', '\r');
	if ( arg == 5 ) return op(' ', 'a');
	if ( arg == 6 ) return op('A', 'B');
	return false;
    }
}
