// @Harness: v2-seman
// @Test: unreachable code
// @Result: UnreachableCode @ 7:16

class unreach23 {
    
    method testm() {
        local a: int;
        for ( ; ; a = 2 ) {
            break;
        }
    }
}
