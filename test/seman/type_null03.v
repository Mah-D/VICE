// @Harness: v2-seman
// @Test: typechecking of operators
// @Result: PASS

class type_null03 {
  method m(f: function(), b: bool) {
	b = f == null;
	b = null == f;
	b = null == null;
	b = f != null;
	b = null != f;
	b = null != null;
	f = null;
  }
}
