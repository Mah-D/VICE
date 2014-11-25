// @Harness: v2-seman
// @Test: typechecking of function types
// @Result: PASS

class type_func01 {
  field f: function();
  method test1() { f = null; }
  method test2(): function() { return null; }
}
