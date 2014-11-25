// @Harness: v2-seman
// @Test: variable initialization (shortcutting of conditionals)
// @Result: PASS

class while_short_init04 {
    
    method testm(p: bool) {
        local foo: bool;
        while ( p and (foo = p) ) testm(foo);
    }
}
