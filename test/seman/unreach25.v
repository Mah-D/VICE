// @Harness: v2-seman
// @Test: unreachable code
// @Result: UnreachableCode @ 10:7

class unreach25 {
    
    method testm() {
        do {
            if ( true ) break;
            else continue;
            local foo: int = 2;
        }
        while ( false );
    }
}
