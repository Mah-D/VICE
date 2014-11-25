// @Harness: v2-exec
// @Test: static method invocations
// @Result: 0=0, 1=4, 2=2, 3=8, 4=0

component static04 {

    method main(arg: int): int {
	if ( arg == 1 ) return val1();
	if ( arg == 2 ) return val2();
	if ( arg == 3 ) return val3();
	return 0;
    }

    method val1(): int { return apply(val2, 2); }
    method val2(): int { return 2; }
    method val3(): int { return apply(val1, 4); }    

    method apply(f: function(): int, a: int): int { 
	return f() + a; 
    }
}
