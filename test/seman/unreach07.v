// @Harness: v2-seman
// @Test: unreachable code
// @Result: UnreachableCode @ 8:7 

class unreach8 {
    
    method testm() {
        while ( true ) {
            {
                break;
            }
            local foo: int = 0;
        }
    }
}
