// @Harness: v2-seman
// @Test: field resolution
// @Result: PASS

class field_res04_a {
    field testf: int;
}
class field_res04_b {
    
    method testm(arg: field_res04_a) {
        local result: int = arg.testf;
    }
}
