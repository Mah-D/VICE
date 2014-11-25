// @Harness: v2-seman
// @@Test: "@Test typechecking of indexing multidimensional arrays"
// @Result: TypeMismatch @ 6:9

class array14 {
    
    method testm(a: int[][]) {
        a[0][false] = 0;
    }
}
