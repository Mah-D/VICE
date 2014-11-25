// @Harness: v2-seman
// @Test: unreachable code
// @Result: UnreachableCode @ 7:5

class unreach5 {
    
    method testm() {
        {
            return;
        }
        local foo: int = 0;
    }
}
