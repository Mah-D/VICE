// @Harness: v2-exec
// @Test: compile-time constants for primitive types
// @Result: 0=42, 1=97, 2=98, 3=10, 4=9, 5=8, 6=13, 7=39, 8=34, 9=255, 10=92, 11=32, 12=42

component char_raw01 {
    method main(arg: int): 8 {
	if ( arg == 1 ) return 'a';
	if ( arg == 2 ) return 'b';
	if ( arg == 3 ) return '\n';
	if ( arg == 4 ) return '\t';
	if ( arg == 5 ) return '\b';
	if ( arg == 6 ) return '\r';
	if ( arg == 7 ) return '\'';
	if ( arg == 8 ) return '"';
	if ( arg == 9 ) return '\377';
	if ( arg == 10 ) return '\\';
	if ( arg == 11 ) return ' ';
	return 0x2a;
    }
}
