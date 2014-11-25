// @Harness: v2-seman
// @Test: variable initialization (ternary expressions)
// @Result: VariableNotInitialized @ 8:12

class ternay_init03 {
    
    method testm(b: bool): int {
        local uninit: int;
        local r: int = b ? 1:(uninit = 0);
        return uninit;
    }
}
