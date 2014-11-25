// @Harness: v2-seman
// @Test: typechecking of operators
// @Result: PASS

class type_func12 {
  method m() {
     local a = { m, null };
     local b = { null, m };
  }
}
