// @Harness: v2-exec
// @Test: compile-time constants for primitive types
// @Result: 0=42, 1=97, 2=98, 3=10, 4=9, 5=8, 6=13, 7=39, 8=34, 9=255, 10=92, 11=32, 12=36, 13=35, 14=126, 15=42

component const02 {
    field a: char = 'a';
    field b: char = 'b';
    field c: char = '\n';
    field d: char = '\t';
    field e: char = '\b';
    field f: char = '\r';
    field g: char = '\'';
    field h: char = '"';
    field i: char = '\377';
    field j: char = '\\';
    field k: char = ' ';
    field l: char = '$';
    field m: char = '#';
    field n: char = '~';

    method main(arg: int): char {
	if ( arg == 1 )  return choose(a, 'a');
	if ( arg == 2 )  return choose(b, 'b');
	if ( arg == 3 )  return choose(c, '\n');
	if ( arg == 4 )  return choose(d, '\t');
	if ( arg == 5 )  return choose(e, '\b');
	if ( arg == 6 )  return choose(f, '\r');
	if ( arg == 7 )  return choose(g, '\'');
	if ( arg == 8 )  return choose(h, '"');
	if ( arg == 9 )  return choose(i, '\377');
	if ( arg == 10 ) return choose(j, '\\');
	if ( arg == 11 ) return choose(k, ' ');
	if ( arg == 12 ) return choose(l, '$');
	if ( arg == 13 ) return choose(m, '#');
	if ( arg == 14 ) return choose(n, '~');
	return '*';
    }

    method choose(a: char, b: char): char {
	if ( a == b ) return a;
	return 'M';
    }
}
