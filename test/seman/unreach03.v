// @Harness: v2-seman
// @Test: unreachable code
// @Result: UnreachableCode @ 8:8

class unreach3 {
    
    method testm(): int {
        while ( true ) {
            continue;
            break;
        }
    }
}
