// @Harness: v2-seman
// @Test: typechecking of operators
// @Result: TypeMismatch @ 6:25

class type_func10 {
  field f: function() = g;
  method g(a: int) { }
}
