// @Harness: v2-seman
// @Test: typechecking; for statements
// @Result: TypeMismatch @ 7:13

class type24 {
    
    method testm() {
        local i: int;
        for ( i = false; ; ) ;
    }
}
