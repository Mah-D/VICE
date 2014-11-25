// @Harness: v2-seman
// @@Test: "@Test typechecking of indexing multidimensional arrays"
// @Result: PASS

class array12 {
    
    method testm(a: int[][]): int {
        return a[0][0];
    }
}
