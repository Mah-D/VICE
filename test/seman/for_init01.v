// @Harness: v2-seman
// @Result: VariableNotInitialized @ 7:13

class for_init01 {
    
    method testm(): bool {
        local uninit: bool;
        for ( ; uninit; ) ;
        return false;
    }
}
