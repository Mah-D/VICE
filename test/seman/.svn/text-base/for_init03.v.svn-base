// @Harness: v2-seman
// @Test: variable initialization
// @Result: VariableNotInitialized @ 12:12

class for_init03 {
    
    method testm(a: int) {
        local foo: int;
        local uninit: int;

        for ( foo = 1; foo < a; foo ) uninit = 2;

        testm(foo);
        testm(uninit);
    }
}
