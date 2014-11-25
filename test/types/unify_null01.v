// @Harness: v2-seman
// @Test: typechecking of operators
// @Result: PASS

class unify_null01 {
  method testm(a: unify_null01, b: boolean) {
    a = b ? this : null;
    a = b ? null : this;
    a = b ? null : null;
    a = null;
    a = this;
  }
}
