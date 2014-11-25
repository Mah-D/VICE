// @Harness: v2-seman
// @Test: typechecking; ternary expressions
// @Result: PASS

class unify_class03_a {
}

class unify_class03_b extends unify_class03_a {
    
    method testm(a: unify_class03_a, b: unify_class03_b) {
        local x: unify_class03_a = false ? a : b;
        local y: unify_class03_a = false ? b : a;
    }
}
