// @Harness: v2-seman
// @@Test: "@Test typechecking of array allocation expressions"
// @Result: TypeMismatch @ 6:11

class array07 {
    
    method testm(): int[] {
        return new bool[2];
    }
}
