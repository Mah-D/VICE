// @Harness: v2-seman
// @Test: type inference for local variables
// @Result: ExpectedVarType @ 6:11

class infer02 {
  method testm(a: int) {
    local foo = null;
  }
}
