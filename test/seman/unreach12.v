// @Harness: v2-seman
// @Test: unreachable code
// @Result: UnreachableCode @ 7:5

class unreach12 {
    
    method testm(): int {
        return 1;
        return 2;
        local foo: int = 0;
    }
}
