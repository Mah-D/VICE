// @Harness: v2-exec
// @Test: compile-time constants for primitive types
// @Result: 0=42, 1=10, 2=9, 3=8, 4=13, 5=34, 6=255

component const04 {
    field str_1: char[] = "";
    field str_2: char[] = "\n\t\b\r\"\377";

    method main(arg: int): char {
	if ( arg == 1 ) return choose(str_2[0], '\n');
	if ( arg == 2 ) return choose(str_2[1], '\t');
	if ( arg == 3 ) return choose(str_2[2], '\b');
	if ( arg == 4 ) return choose(str_2[3], '\r');
	if ( arg == 5 ) return choose(str_2[4], '"');
	if ( arg == 6 ) return choose(str_2[5], '\377');
	return '*';
    }

    method choose(a: char, b: char): char {
	if ( a == b ) return a;
	return 'M';
    }
}
