// @Harness: v2-seman
// @Test: typechecking; invocation of method on non-object
// @Result: PASS

class nmspc04 {
    
    method testm() {
        local foo: int = 1;
        local int: int = 0;
    }
}
