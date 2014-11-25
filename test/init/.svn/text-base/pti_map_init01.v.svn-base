// @Harness: v2-init
// @Test: static method invocations
// @Result: PASS

component map_init01 {
    field a: int[];
    field b: int[];

    constructor() {
        local array = { 1, 2, 3, 4, 5 };
	a = array;
        b = map(plusTwo, a);
    }

    method plusTwo(a: int): int {
	return a + 2;
    }

    method map<A, B>(f: function(A): B, a: A[]): B[] {
	local cntr = 0;
	local b = new B[a.length];
	for ( ; cntr < a.length; cntr++ ) b[cntr] = f(a[cntr]);
	return b;
    }
 }

/* 
heap {
    record #0:2:map_init01 {
        field a: int[] = #1:int[5];
        field b: int[] = #2:int[5];
    }
    record #1:5:int[5] {
	field 0: int = int:1;
	field 1: int = int:2;
	field 2: int = int:3;
	field 3: int = int:4;
	field 4: int = int:5;
    }
    record #2:5:int[5] {
	field 0: int = int:3;
	field 1: int = int:4;
	field 2: int = int:5;
	field 3: int = int:6;
	field 4: int = int:7;
    }
} 
*/
