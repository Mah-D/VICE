// @Harness: v2-seman
// @Test: variable initialization
// @Result: VariableNotInitialized @ 13:12

class switch_init03 {
    
    method testm(a: int) {
        local uninit: int;
        switch ( a ) {
            case ( 1 ) uninit = 2;
            case ( 2, 3, 4 ) uninit = 5;
            case ( 78788 ) testm(a - 1);
            default uninit = 11;
        }
        testm(uninit);
    }
}
