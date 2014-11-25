// @Harness: v2-exec
// @Test: static method invocations
// @Result: 0=0, 1=12, 2=12, 3=10, 4=16, 5=0

component static05 {
    field m: function(): int = val1;
    field av: int = m();
    field bv: int = val2();
    field cv: int = val3();

    method main(arg: int): int {
	if ( arg == 1 ) return m();
	if ( arg == 2 ) return val1();
	if ( arg == 3 ) return val2();
	if ( arg == 4 ) return val3();
	return 0;
    }

    method val1(): int { return apply(val2, 2); }
    method val2(): int { return 10; }
    method val3(): int { return apply(val1, 4); }    

    method apply(f: function(): int, a: int): int { 
	return f() + a; 
    }
}
