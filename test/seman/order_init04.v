// @Harness: v2-seman
// @Test: variable initialization (order of evaluation)
// @Result: VariableNotInitialized @ 7:19

class order_init04 {
    
    method testm(a: int, b: int): int {
        local uninit: int;
        return testm(uninit, uninit = 0);
    }
}
