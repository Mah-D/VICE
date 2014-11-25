// @Harness: v2-seman
// @Test: variable initialization
// @Result: VariableNotInitialized @ 10:12

class switch_init01 {
    
    method testm(a: int) {
        local uninit: int;
        switch ( 0 ) {
            case ( 1 ) uninit = 2;
        }
        testm(uninit);
    }
}
