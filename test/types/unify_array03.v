// @Harness: v2-seman
// @Test: typechecking; ternary expressions
// @Result: CannotUnifyBranchTypes @ 8:38

class unify_array03 {
    
    method testm(a: int[], b: int) {
        local x: int[] = false ? a : b;
    }
}
