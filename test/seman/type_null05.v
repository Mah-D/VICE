// @Harness: v2-seman
// @Test: typechecking of operators
// @Result: UnresolvedBinOp @ 7:13

class type_null05 {
  method testm(a: int) {
    a = null + 0;
  }
}
