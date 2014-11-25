// @Harness: v2-seman
// @Test: variable initialization (ternary expressions)
// @Result: PASS

class ternay_short_init01 {
    
    method testm(p: bool) {
        local foo: bool;
        local result: bool = p or (foo = p) ? p:foo;
    }
}
