// @Harness: v2-seman
// @Test: inheritance of fields
// @Result: PASS

class inh12_a {
    field testf: int;
}
class inh12_b extends inh12_a {
    
    method testm() {
        testf = 0;
    }
}
