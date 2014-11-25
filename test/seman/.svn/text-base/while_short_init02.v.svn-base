// @Harness: v2-seman
// @Test: variable initialization (shortcutting of conditionals)
// @Result: PASS

class while_short_init02 {
    
    method testm(p: bool) {
        local foo: bool;
        while ( p or (foo = p) ) foo = p;
        testm(foo);
    }
}
