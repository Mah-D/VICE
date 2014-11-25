// @Harness: v2-seman
// @Test: variable initialization (shortcutting of conditionals)
// @Result: VariableNotInitialized @ 8:14

class short_init01 {
    
    method testm(p: bool) {
        local uninit: bool;
        if ( p or (uninit = p) ) {
            testm(uninit);
        }
    }
}
