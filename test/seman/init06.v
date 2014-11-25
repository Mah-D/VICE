// @Harness: v2-seman
// @Test: variable initialization
// @Result: VariableNotInitialized @ 7:23

class init06 {
    
    method testm() {
        local foo: int;
        foo = foo = foo = foo;
    }
}
