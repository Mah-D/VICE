// @Harness: v2-seman
// @Test: variable initialization (order of evaluation)
// @Result: PASS

class order_init05 {
    
    method testm(a: bool) {
        local foo: bool;
        if ( foo = true ) ;
        testm(foo);
    }
}
