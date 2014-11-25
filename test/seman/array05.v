// @Harness: v2-seman
// @@Test: "@Test typechecking of array elements"
// @Result: TypeMismatch @ 6:12

class array05 {
    
    method testm(a: int[]) {
        a[0] = false;
    }
}
