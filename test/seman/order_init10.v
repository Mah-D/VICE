// @Harness: v2-seman
// @Test: variable initialization (order of evaluation)
// @Result: PASS

class order_init10 {
    
    method testm() {
        local foo: int;
        local a: int[] = { foo = 0, foo };
    }
}
