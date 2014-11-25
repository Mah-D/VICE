// @Harness: v2-seman
// @Test: typechecking; ternary expressions
// @Result: PASS

class unify_class04_a {
}

class unify_class04_b extends unify_class04_a {
}

class unify_class04_c extends unify_class04_a {
    
    method testm(a: unify_class04_b, b: unify_class04_c) {
        local x: unify_class04_a = false ? a : b;
        local y: unify_class04_a = false ? b : a;
    }
}
