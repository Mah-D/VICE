// @Harness: v2-seman
// @Test: variable initialization (shortcutting of conditionals)
// @Result: PASS

class dowhile_init04 {
    
    method testm(p: bool) {
        local foo: bool;
        do {
            foo = p;
        }
        while ( p and (foo = p) );
        testm(foo);
    }
}
