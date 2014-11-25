// @Harness: v2-seman
// @Test: unreachable code
// @Result: UnreachableCode @ 7:8 

class unreach6 {
    
    method testm() {
        return;
        {
            local foo: int = 0;
        }
    }
}
