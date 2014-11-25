// @Harness: v2-seman
// @Test: global identifier resolution
// @Result: ExpectedVarType @ 8:19

component local6 {
    
    method testm() {
        local foo: local6;
    }
}
