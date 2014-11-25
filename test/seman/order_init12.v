// @Harness: v2-seman
// @Test: variable initialization (order of operations)
// @Result: VariableNotInitialized @ 7:11

class order_init12 {
    
    method testm(a: order_init12): int {
        local uninit: order_init12;
        return uninit.testm(uninit = a);
    }
}
