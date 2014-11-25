// @Harness: v2-seman
// @Test: variable initialization (shortcutting of conditionals)
// @Result: PASS

class for_short_init02 {
    
    method testm(p: bool) {
        local foo: bool;
        for ( ; p and (foo = p); ) {
            testm(foo);
        }
    }
}
