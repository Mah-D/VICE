// @Harness: v2-seman
// @Test: typechecking; primitive operations
// @Result: TypeMismatch @ 6:16

class type40 {
    
    method testm() {
        local a: int = 2 <= null;
    }
}
