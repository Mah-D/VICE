// @Harness: v2-seman
// @Test: variable initialization
// @Result: VariableNotInitialized @ 8:37

class for_init05 {
    
    method testm(a: int) {
        local foo: int;
        local uninit: int;
        for ( foo = 0; foo < 100; foo = uninit ) {
            if ( foo == 12 ) continue;
            uninit = 12;
        }
    }
}
