// @Harness: v2-seman
// @Test: variable initialization (shortcutting of conditionals)
// @Result: VariableNotInitialized @ 9:14

class for_short_init01 {
    
    method testm(p: bool) {
        local uninit: bool;
        for ( ; p or (uninit = p); ) {
            testm(uninit);
        }
    }
}
