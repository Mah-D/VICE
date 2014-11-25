// @Harness: v2-seman
// @Test: variable initialization (order of operations)
// @Result: PASS

class order_init09 {
    
    method testm(a: int[]): int {
        local foo: int;
        return a[foo] = foo = 0;
    }
}
