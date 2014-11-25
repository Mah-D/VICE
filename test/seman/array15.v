// @Harness: v2-seman
// @@Test: "@Test typechecking of elements of multidimensional arrays"
// @Result: TypeMismatch @ 6:12

class array15 {
    
    method testm(a: int[][]) {
        a[0][0] = false;
    }
}
