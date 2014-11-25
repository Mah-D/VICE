// @Harness: v2-seman
// @Test: typechecking; primitive operations
// @Result: UnresolvedOperator @ 7:23

component inc08 {
    method testm(a: function()) {
        local foo = --a;
    }
}
