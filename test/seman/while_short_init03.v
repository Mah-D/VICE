// @Harness: v2-seman
// @Test: variable initialization (shortcutting of conditionals)
// @Result: VariableNotInitialized @ 8:12

class while_short_init03 {
    
    method testm(p: bool) {
        local uninit: bool;
        while ( p and (uninit = p) ) ;
        testm(uninit);
    }
}
