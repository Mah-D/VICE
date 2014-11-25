// @Harness: v2-seman
// @Test: typechecking; ternary expressions
// @Result: PASS

class unify_array02 {
    
    method testm(a: int[], b: int[]) {
        local x: int[] = false ? a : null;
        local y: int[] = false ? null : b;
    }
}
