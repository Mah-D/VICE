// @Harness: v2-init
// @Test: static method invocations
// @Result: PASS

component sort_init03 {
    field a: char[][] = { "agg", "acc", "zxy" };

    constructor() {
	sort(a, strCmp);
    }

    method strCmp(a: char[], b: char[]): bool {
	local i: int;
	for ( i = 0; i < a.length; i++ ) {
	    if ( i >= b.length or a[i] < b[i] ) return true;
	}
	return false;
    }

    method sort<T>(a: T[], lt: function(T, T): bool) {
	local i: int, j: int, len = a.length;
	for ( i = 0; i < len; i++ ) {
	    for ( j = i+1; j < len; j++ ) {
		local x = a[i], y = a[j];
		if ( lt(y, x) ) {
		    a[j] = x;
		    a[i] = y;
		}
	    }
	}
    }
 }

/* 
heap {
    record #0:2:sort_init03 {
        field a: char[] = #1:char[][3];
    }
    record #1:3:char[][3] {
	field 0: char[] = #3:char[3];
	field 1: char[] = #2:char[3];
	field 2: char[] = #4:char[3];
    }
    record #2:3:char[3] {
	field 0: char = char:97;
	field 1: char = char:103;
	field 2: char = char:103;
    }
    record #3:3:char[3] {
	field 0: char = char:97;
	field 1: char = char:99;
	field 2: char = char:99;
    }
    record #4:3:char[3] {
	field 0: char = char:122;
	field 1: char = char:120;
	field 2: char = char:121;
    }
} 
*/
