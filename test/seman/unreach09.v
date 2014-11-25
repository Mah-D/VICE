// @Harness: v2-seman
// @Test: unreachable code
// @Result: UnreachableCode @ 8:8 

class unreach9 {
    
    method testm() {
        while ( true ) {
            break;
            {
                local foo: int = 0;
            }
        }
    }
}
