// @Harness: v2-seman
// @Test: class inheritance
// @Result: CannotOverrideArity @ 13:8

class inh9_a {
    
    method testm() {
    }
}
class inh9_b extends inh9_a {
    
    method testm() {
        local foo: int = 0;
    }
}
class inh9_c extends inh9_b {
    
    method testm(a: int) {
        local foo: int = a;
    }
}
