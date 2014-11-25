// @Harness: v2-seman
// @Test: variable initialization
// @Result: PASS

class switch_init05 {
    
    method testm(a: int) {
        local foo: int;
        switch ( a ) {
            case ( 1 ) foo = 1;
            case ( 2 ) foo = 2;
            case ( 3 ) foo = 3;
            default foo = 4;
        }
        testm(foo);
    }
}
