// @Harness: v2-seman
// @Test: field resolution
// @Result: PASS

class field_res03_a {
    field testf: int;
}
class field_res03_b {
    
    method testm(arg: field_res03_a) {
        arg.testf = 0;
    }
}
