// @Harness: v2-seman
// @Test: unreachable code
// @Result: UnreachableCode @ 8:7 

class unreach24 {
    
    method testm() {
        do {
            break;
            local foo: int = 2;
        }
        while ( false );
    }
}
