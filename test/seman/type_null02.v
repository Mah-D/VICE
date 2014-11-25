// @Harness: v2-seman
// @Test: typechecking of operators
// @Result: PASS

class type_null02 {
  method testm(b: bool) {
    b = testm == null;
    b = null == testm;
    b = testm != null;
    b = null != testm;
  }
}
