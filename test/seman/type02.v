// @Harness: v2-seman
// @Test: typechecking; invocation of method on non-object
// @Result: UnresolvedMember @ 6:6

class type02 {
    
    method testm(a: int) {
        a.foo();
    }
}
