// @Harness: v2-seman
// @Result: PASS

component sort01 {

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
