// @Harness: v2-seman
// @Test: typechecking; primitive operations
// @Result: TypeMismatch @ 6:16

class type30 {
    
    method testm() {
        local a: int = 2 / null;
    }
}
