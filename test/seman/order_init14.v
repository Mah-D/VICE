// @Harness: v2-seman
// @Test: variable initialization (order of operations)
// @Result: PASS

class order_init14 {
    
    method testm(): int[][] {
        local foo: int;
        return new int[foo = 0][foo];
    }
}
