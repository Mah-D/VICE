// @Harness: v2-seman
// @Test: variable initialization
// @Result: VariableNotInitialized @ 8:15

class init02 {
    
    method testm() {
        local foo: int;
        if ( true ) foo = 2;
        local bar: int = foo;
    }
}
