// @Harness: v2-seman
// @Test: variable initialization (shorcutting of conditionals)
// @Result: VariableNotInitialized @ 9:12

class for_short_init04 {
    
    method testm(p: bool) {
        local uninit: bool;
        for ( ; p and (uninit = p); ) ;
        testm(uninit);
    }
}
