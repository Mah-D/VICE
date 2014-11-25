// @Harness: v2-seman
// @@Test: "Test typechecking of multidimensional arrays"
// @Result: TypeMismatch @ 6:11

class array17 {
    
    method testm(): int[][] {
        return new bool[2][2];
    }
}
