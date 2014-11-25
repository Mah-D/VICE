// @Harness: v2-seman
// @Test: typechecking; ternary expressions
// @Result: PASS

class unify_char_int02 {
    
    method testm() {
        local a = { '0', 1 };
	local b: int[] = a;
    }
}
