// @Harness: v2-seman
// @Test: variable initialization (ternary expressions)
// @Result: VariableNotInitialized @ 8:14

class ternay_short_init03 {
    
    method testm(p: bool) {
        local uninit: bool;
        local result: bool = p or (uninit = p) ? p:p;
        result = uninit;
    }
}
