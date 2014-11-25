// @Harness: v2-seman
// @Test: variable initialization (shorcutting of conditionals)
// @Result: VariableNotInitialized @ 8:39

class for_short_init05 {
    
    method testm(p: bool) {
        local uninit: bool;
        for ( ; p or (uninit = p); testm(uninit) ) ;
    }
}
