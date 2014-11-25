// @Harness: v2-seman
// @Test: typechecking; ternary expressions
// @Result: PASS

class unify_class05_a {
}

class unify_class05_b {
}

class unify_class05_c extends unify_class05_b {
}

class unify_class05_d extends unify_class05_b {
    
    method testm(a: unify_class05_c, b: unify_class05_d) {
        local x: unify_class05_b = false ? a : b;
        local y: unify_class05_b = false ? b : a;
    }
}
