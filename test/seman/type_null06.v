// @Harness: v2-seman
// @Test: typechecking of operators
// @Result: TypeMismatch @ 7:13

class type_null06 {
  method testm(a: int) {
    a = 0 + null;
  }
}
