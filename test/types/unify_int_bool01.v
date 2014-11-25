// @Harness: v2-seman
// @Test: typechecking; ternary expressions
// @Result: CannotUnifyBranchTypes @ 6:24

class unify_int_bool01 {
    
    method testm() {
        local a: int = false ? 0 : false;
    }
}
