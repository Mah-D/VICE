// @Harness: v2-seman
// @Test: variable initialization
// @Result: VariableNotInitialized @ 11:16

class init21 {
    
    method testm() {
        local foo: int;
        do {
            if ( true ) continue;
            foo = 0;
        }
        while ( foo < 2 );
    }
}
