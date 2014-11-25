// @Harness: v2-seman
// @Test: typechecking; for statement
// @Result: TypeMismatch @ 6:13

class type19 {
    
    method testm() {
        for ( ; 0; ) ;
    }
}
