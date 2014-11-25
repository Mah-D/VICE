// @Harness: v2-seman
// @Test: typechecking; primitive operations
// @Result: UnresolvedOperator @ 7:21

class inc02 {
    method testm(a: bool) {
        local foo = a++;
    }
}
