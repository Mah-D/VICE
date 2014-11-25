// @Harness: v2-seman
// @Test: unreachable code
// @Result: UnreachableCode @ 7:7 

class unreach11 {
    
    method testm() {
        {
            return;
        }
        {
            local foo: int;
        }
    }
}
