// @Harness: v2-seman
// @Test: inheritance of fields
// @Result: PASS

class inh13_a {
    field testf: int;
}
class inh13_b extends inh13_a {
    
    method testm(foo: inh13_b) {
        foo.testf = 0;
    }
}
