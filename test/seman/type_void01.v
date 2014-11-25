// @Harness: v2-seman
// @Test: typechecking of operators
// @Result: CannotCompareValues @ 7:10

class type_void01 {
  method testm() {
    if ( testm() == null ) ;
  }
}
