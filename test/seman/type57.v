// @Harness: v2-seman
// @Test: typechecking of array initializers
// @Result: TypeMismatch @ 6:15 

class type57 {
    
    method testm() {
        local a: int[] = { false };
    }
}
