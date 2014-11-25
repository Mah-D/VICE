// @Harness: v2-seman
// @Test: duplicate parameter names
// @Result: ParameterRedefined @ 5:27

class param1 {
    
    method testm(a: int, a: char) {
        a = 0;
    }
}
