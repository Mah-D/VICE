// @Harness: v2-seman
// @Test: variable initialization (ternary expressions)
// @Result: PASS

class ternay_init02 {
    
    method testm(b: bool): int {
        local foo: int;
        local r: int = b ? (foo = 0):(foo = 1);
        return foo;
    }
}
