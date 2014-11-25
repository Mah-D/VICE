// @Harness: v2-seman
// @Test: variable initialization
// @Result: VariableNotInitialized @ 10:41

class for_init04 {
    
    method testm(a: int) {
        local foo: int;
        local uninit: int;

        for ( foo = 0; foo < 100; foo = uninit ) {
            if ( foo == 12 ) uninit = 2;
        }
    }
}
