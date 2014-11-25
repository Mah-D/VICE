// @Harness: v2-seman
// @Test: variable initialization (shortcutting of conditionals)
// @Result: PASS

class dowhile_short_init01 {
    
    method testm(p: bool) {
        local foo: bool;
        do {
        }
        while ( p or (foo = p) );
        testm(foo);
    }
}
