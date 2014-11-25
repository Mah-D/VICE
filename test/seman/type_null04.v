// @Harness: v2-seman
// @Test: typechecking of operators
// @Result: PASS

class type_null04 {
  method testm(a: int[], b: bool) {
    b = a == null;
    b = null == a;
    b = null == null;
    b = a != null;
    b = null != a;
    b = null != null;
    a = null;
  }
}
