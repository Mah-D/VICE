// @Harness: v2-seman
// @Test: variable initialization (order of operations)
// @Result: PASS

class order_init13 {
    field test: int;
    
    method testm(a: order_init13): int {
        local foo: order_init13;
        return (foo = a).test;
    }
}
