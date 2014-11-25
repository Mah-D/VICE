// @Harness: v2-seman
// @Test: variable initialization (order of evaluation)
// @Result: VariableNotInitialized @ 7:12

class order_init02 {
    
    method testm(): int {
        local uninit: int;
        return uninit + (uninit = 2);
    }
}
