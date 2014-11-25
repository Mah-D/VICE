// @Harness: v2-seman
// @Test: typechecking; invocation of method on non-object
// @Result: PASS

class pts_nmspc01<X> {
    
    field f: X;

    method testm() {
        local foo: int = 1;
        local int: int = new pts_nmspc01<int>().f;
    }
}
