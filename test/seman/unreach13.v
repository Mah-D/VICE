// @Harness: v2-seman
// @Test: unreachable code
// @Result: UnreachableCode @ 8:11

class unreach13 {
    
    method testm(): int {
        while ( true ) {
            break;
            {
                local foo: int = 0;
            }
        }
    }
}
