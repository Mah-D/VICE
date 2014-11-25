// @Harness: v2-seman
// @Test: typechecking; primitive operations
// @Result: UnresolvedBinOp @ 6:16

class type33 {
    
    method testm() {
        local a: int = 2 ^ null;
    }
}
