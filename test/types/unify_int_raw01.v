// @Harness: v2-seman
// @Test: typechecking; ternary expressions
// @Result: PASS

class unify_int_raw01 {
    
    method testm() {
        local a: 32 = false ? 0 : 0x0f;
    }
}
