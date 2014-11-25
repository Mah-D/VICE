// @Harness: v2-seman
// @Test: variable initialization (shortcutting of conditionals)
// @Result: VariableNotInitialized @ 9:12

class dowhile_init03 {
    
    method testm(p: bool) {
        local foo: bool;
        do {
        }
        while ( p and (foo = p) );
        testm(foo);
    }
}
