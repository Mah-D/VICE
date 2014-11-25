// @Harness: v2-seman
// @Test: unreachable code
// @Result: UnreachableCode @ 8:9

class unreach2 {
    
    method testm(): int {
        while ( true ) {
            break;
            local foo: int = 0;
        }
    }
}
