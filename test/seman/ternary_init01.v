// @Harness: v2-seman
// @Test: variable initialization (ternary expressions)
// @Result: VariableNotInitialized @ 8:12

class ternay_init01 {
    
    method testm(b: bool): int {
        local uninit: int;
        local r: int = b ? (uninit = 0):1;
        return uninit;
    }
}
