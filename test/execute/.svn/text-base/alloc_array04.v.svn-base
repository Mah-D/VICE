// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=0, 2=0, 3=0, 4=0, 5=42

component alloc_array04 {
    field a: int[] =  a_int();
    field b: bool[] =  a_bool();
    field c: char[] = a_char();
    field d: char[] = a_str();

    method a_int(): int[] {
	local a: int[] = {};
        return a;
    }

    method a_bool(): bool[] {
	local a: bool[] = {};
        return a;
    }

    method a_char(): char[] {
	local a: char[] = {};
        return a;
    }

    method a_str(): char[] {
        return "";
    }

    method main(arg: int): int {
	if ( arg == 1 ) return a.length;
	if ( arg == 2 ) return b.length;
	if ( arg == 3 ) return c.length;
	if ( arg == 4 ) return d.length;
	return 42;
    }
}
