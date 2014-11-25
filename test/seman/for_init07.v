// @Harness: v2-seman
// @Test: variable initialization
// @Result: PASS

class for_init07 {
    
    method testm(a: int) {
        local foo: int;
        local bar: int;
        for ( foo = 0; foo < 100; foo = bar ) {
            if ( foo == a ) {
                bar = 2;
                continue;
            }
            if ( foo == a - 1 ) {
                break;
            }
            bar = 3;
        }
    }
}
