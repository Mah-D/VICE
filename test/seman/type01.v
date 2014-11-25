// @Harness: v2-seman
// @Test: typechecking; invocation of method on non-object
// @Result: UnresolvedMember @ 7:8

class type01 {
    
    method testm() {
        local foo: int = 1;
        foo.bar();
    }
}
