// @Harness: v2-seman
// @Test: typechecking; ternary expressions
// @Result: PASS

class unify_array01 {
    
    method testm(a: int[], b: int[]) {
        local x: int[] = false ? a : b;
    }
}
