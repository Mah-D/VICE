// @Harness: v2-seman
// @Test: typechecking of operators
// @Result: PASS

class type_null01 {
  method testm(a: type_null01, b: bool) {
    b = this == null;
    b = null == this;
    b = null == null;
    b = this != null;
    b = null != this;
    b = null != null;
    a = null;
  }
}
