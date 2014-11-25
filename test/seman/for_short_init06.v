// @Harness: v2-seman
// @Test: variable initialization (shorcutting of conditionals)
// @Result: PASS

class for_short_init06 {
    
    method testm(p: bool) {
        local foo: bool;
        for ( ; p and (foo = p); testm(foo) ) ;
    }
}
