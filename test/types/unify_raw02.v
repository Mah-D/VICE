// @Harness: v2-seman
// @Test: typechecking; ternary expressions
// @Result: PASS

class unify_raw02 {
    
    method testm() {
        local a: 12 = false ? 0xafa : 0x0f;
    }
}
