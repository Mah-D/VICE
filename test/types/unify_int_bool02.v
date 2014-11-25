// @Harness: v2-seman
// @Test: typechecking; ternary expressions
// @Result: CannotUnifyElemTypes @ 6:24

class unify_int_bool02 {
    
    method testm() {
        local a = { 0, false };
    }
}
