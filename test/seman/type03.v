// @Harness: v2-seman
// @Test: typechecking; invocation of method on non-object
// @Result: UnresolvedMember @ 8:6

class type03 {
    field a: int = 0;
    
    method testm() {
        a.foo();
    }
}
