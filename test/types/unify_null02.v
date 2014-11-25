// @Harness: v2-seman
// @Test: typechecking of operators
// @Result: PASS

class unify_null02 {
  method testm(a: unify_null02, b: boolean) {
    local x = b ? this : null;
    a = x;
    local y = b ? null : this;
    a = y;
  }
}
