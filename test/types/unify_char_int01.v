// @Harness: v2-seman
// @Test: typechecking; ternary expressions
// @Result: PASS

class unify_char_int01 {
    
    method testm() {
        local a: int = false ? '0' : 1;
    }
}
