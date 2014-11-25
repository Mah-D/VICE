// @Harness: v2-seman
// @Test: array initializers test
// @Result: CannotInferEmptyArrayType @ 10:19

class type67 {
    
    field a: int[] = {};

    method testm() {
        local a = { };
    }
}
