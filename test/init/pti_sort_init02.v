// @Harness: v2-init
// @Test: static method invocations
// @Result: PASS

component sort_init02 {
    field a: char[] = "abxze";

    constructor() {
	sort(a, lessThan);
    }

    method lessThan(a: char, b: char): bool {
	return a < b;
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
    record #0:2:sort_init02 {
        field a: char[] = #1:char[5];
    }
    record #1:5:int[5] {
	field 0: char = char:97;
	field 1: char = char:98;
	field 2: char = char:101;
	field 3: char = char:120;
	field 4: char = char:122;
    }
} 
*/
