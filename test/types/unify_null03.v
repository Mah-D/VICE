// @Harness: v2-seman
// @Test: typechecking of operators
// @Result: PASS

class unify_null03 {
  method testm(a: unify_null03) {
    local x = { this, null };
    local ax: unify_null03[] = x;
    local y = { null, this };
    local ay: unify_null03[] = y;
  }
}
