// @Harness: v2-seman
// @Test: field resolution
// @Result: UnresolvedMember @ 10:22

class field_res02_a {
    field testf: int;
}
class field_res02_b {
    
    method testm(arg: field_res02_a) {
        local result: int = arg.unres;
    }
}
