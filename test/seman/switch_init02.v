// @Harness: v2-seman
// @Test: variable initialization
// @Result: VariableNotInitialized @ 13:12

class switch_init02 {
    
    method testm(a: int) {
        local uninit: int;
        switch ( 0 ) {
            case ( 1 ) ;
            default {
                uninit = 2;
            }
        }
        testm(uninit);
    }
}
