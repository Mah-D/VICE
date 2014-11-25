// @Harness: v2-seman
// @Test: variable initialization (shortcutting of conditionals)
// @Result: PASS

class short_init03 {
    
    method testm(p: bool) {
        local foo: bool;
        if ( p or (foo = p) ) ;
        else testm(foo);
    }
}
