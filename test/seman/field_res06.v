// @Harness: v2-seman
// @Test: field resolution
// @Result: UnresolvedMember @ 10:17

component field_res06_a {
    field testf: int;
}
component field_res06_b {
    
    method testm() {
        field_res06_a.unres = 0;
    }
}
