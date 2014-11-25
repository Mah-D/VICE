// @Harness: v2-seman
// @Test: typechecking; invocation of method on non-object
// @Result: PASS

class nmspc01 {
    
    field int: bool;

    method testm() {
        local foo: int = 1;
    }
}
