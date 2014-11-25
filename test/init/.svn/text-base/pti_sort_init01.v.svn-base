// @Harness: v2-init
// @Test: static method invocations
// @Result: PASS

component sort_init01 {
    field a: int[] = { 5, 2, 1, 3, 4 };

    constructor() {
	sort(a, lessThan);
    }

    method lessThan(a: int, b: int): bool {
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
    record #0:2:sort_init01 {
        field a: int[] = #1:int[5];
    }
    record #1:5:int[5] {
	field 0: int = int:1;
	field 1: int = int:2;
	field 2: int = int:3;
	field 3: int = int:4;
	field 4: int = int:5;
    }
} 
*/
