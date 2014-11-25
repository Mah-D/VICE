// @Harness: v2-seman
// @Test: typechecking; ternary expressions
// @Result: CannotUnifyBranchTypes @ 8:38

class unify_func03 {
    
    method testm(a: function()) {
        local x: function() = false ? a : 0;
    }
}
