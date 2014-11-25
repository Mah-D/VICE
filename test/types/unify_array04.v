// @Harness: v2-seman
// @Test: typechecking; ternary expressions
// @Result: CannotUnifyBranchTypes @ 8:38

class unify_array04 {
    
    method testm(a: int[], b: char[]) {
        local x: int[] = false ? a : b;
    }
}
