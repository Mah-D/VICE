// @Harness: v2-seman
// @Test: variable initialization
// @Result: VariableNotInitialized @ 7:12

class init03 {
    
    method testm(): int {
        local foo: int;
        return foo;
    }
}
