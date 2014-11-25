// @Harness: v2-seman
// @Test: typechecking; primitive operations
// @Result: UnresolvedOperator @ 7:23

class inc01 {
    method testm(a: bool) {
        local foo = --a;
    }
}
