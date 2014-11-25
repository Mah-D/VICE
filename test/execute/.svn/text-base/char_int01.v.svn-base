// @Harness: v2-exec
// @Test: compile-time constants for primitive types
// @Result: 0=0, 1=1, 2=1, 3=1, 4=1, 5=1, 6=1, 7=1, 8=1, 9=1, 10=1, 11=1, 12=0

component char_int01 {
    field a: int = 'a';
    field b: int = 'b';
    field c: int = '\n';
    field d: int = '\t';
    field e: int = '\b';
    field f: int = '\r';
    field g: int = '\'';
    field h: int = '"';
    field i: int = '\377';
    field j: int = '\\';
    field k: int = ' ';
    method main(arg: int): bool {
	if ( arg == 1 )  return a == ('a' :: int);
	if ( arg == 2 )  return b == ('b' :: int);
	if ( arg == 3 )  return c == ('\n' :: int);
	if ( arg == 4 )  return d == ('\t' :: int);
	if ( arg == 5 )  return e == ('\b' :: int);
	if ( arg == 6 )  return f == ('\r' :: int);
	if ( arg == 7 )  return g == ('\'' :: int);
	if ( arg == 8 )  return h == ('"' :: int);
	if ( arg == 9 )  return i == ('\377' :: int);
	if ( arg == 10 ) return j == ('\\' :: int);
	if ( arg == 11 ) return k == (' ' :: int);
	return false;
    }
}
