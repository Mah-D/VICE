// @Harness: v2-seman
// @Test: variable initialization (shortcutting of conditionals)
// @Result: VariableNotInitialized @ 8:12

class short_init04 {
    
    method testm(p: bool) {
        local uninit: bool;
        if ( p and (uninit = p) ) ;
        testm(uninit);
    }
}
