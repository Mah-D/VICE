// @Harness: v2-seman
// @Test: global identifier resolution
// @Result: ExpectedVarType @ 8:19

class local3 {
    
    method testm() {
        local foo = bar();
    }

    method bar() {
    }
}
