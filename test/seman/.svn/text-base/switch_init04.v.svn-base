// @Harness: v2-seman
// @Test: variable initialization
// @Result: VariableNotInitialized @ 10:12

class switch_init04 {
    
    method testm(a: int) {
        local uninit: int;
        switch ( a ) {
            case ( 2 ) uninit = 1;
        }
        testm(uninit);
    }
}
