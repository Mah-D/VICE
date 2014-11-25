// @Harness: v2-seman
// @Test: duplicate parameter names
// @Result: ParameterRedefined @ 5:34

class param2 {
    
    method testm(a: int, b: int, a: char) {
        a = 0;
    }
}
