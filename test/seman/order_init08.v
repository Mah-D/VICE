// @Harness: v2-seman
// @Test: variable initialization (order of evaluation)
// @Result: VariableNotInitialized @ 7:23

class order_init08 {
    
    method testm(a: int[]): int {
        local uninit: int;
        return a[uninit = 0] = uninit;
    }
}
