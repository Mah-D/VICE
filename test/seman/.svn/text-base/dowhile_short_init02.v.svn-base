// @Harness: v2-seman
// @Test: variable initialization (shortcutting of conditionals)
// @Result: VariableNotInitialized @ 10:12

class dowhile_init02 {
    
    method testm(p: bool) {
        local foo: bool;
        do {
            if ( p ) break;
        }
        while ( p or (foo = p) );
        testm(foo);
    }
}
