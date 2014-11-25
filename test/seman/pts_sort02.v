// @Harness: v2-seman
// @Result: PASS

component sort02 {

    method test() {
	local a = { 0, 21, 2, 34, 5, 6, 68, -11, -90 };
	sort(a, int_gt);
    }

    method int_gt(i: int, j: int): bool {
	return i > j;
    }

    method sort<T>(a: T[], gt: function(T, T): bool) {
        local i: int, j: int, len = a.length;
        for ( i = 0; i < len; i++ ) {
            for ( j = i+1; j < len; j++ ) {
                if ( gt(a[i], a[j]) ) {
                   local t = a[i];
                   a[i] = a[j];
                   a[j] = t;
                }
            }
        }
    }
}
