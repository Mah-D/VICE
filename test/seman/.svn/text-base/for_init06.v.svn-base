// @Harness: v2-seman
// @Test: variable initialization
// @Result: VariableNotInitialized @ 12:11

class for_init06 {
    
    method testm(a: int) {
        local foo: int;
        local uninit: int;
        for ( foo = 1; foo < 100; foo = uninit ) {
            if ( foo == a ) break;
            uninit = 2;
        }
        foo = uninit;
    }
}
