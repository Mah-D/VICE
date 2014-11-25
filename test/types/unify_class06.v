// @Harness: v2-seman
// @Test: typechecking; ternary expressions
// @Result: CannotUnifyBranchTypes @ 8:49

class unify_class06 {
    
    method testm(x: int) {
        local a: unify_class06 = false ? this : x;
    }
}
