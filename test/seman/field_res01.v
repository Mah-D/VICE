// @Harness: v2-seman
// @Test: field resolution
// @Result: UnresolvedMember @ 10:9

class field_res01_a {
    field testf: int;
}
class field_res01_b {
    
    method testm(arg: field_res01_a) {
        arg.unres = 0;
    }
}
