// @Harness: v2-seman
// @Test: variable initialization (order of evaluation)
// @Result: PASS

class order_init03 {
    
    method testm(a: int, b: int): int {
        local foo: int;
        return testm(foo = 0, foo);
    }
}
